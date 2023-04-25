package com.sugo.app.feat.ui.note.Chat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.sugo.app.R
import com.sugo.app.databinding.FragmentChattingBinding
import com.sugo.app.feat.model.request.Chat
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.ui.common.ImageRealPath
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.common.chatLong
import com.sugo.app.feat.ui.common.loadImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModels { ViewModelFactory(requireContext()) }
    private val list = ArrayList<Uri>()
    private lateinit var binding: FragmentChattingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * 리팩토링 필수 일단 기능먼저 개벌
     *
     *
     * **/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageUrl = ""
        var noteId = requireArguments().getString("noteId")!!.substringBefore(".").toLong()
        val productPostId = chatLong(requireArguments().getString("productPostId")!!).toLong()
        val creatingUserId = chatLong(requireArguments().getString("creatingUserId")!!).toLong()
        val opponentUserId = chatLong(requireArguments().getString("opponentUserId")!!).toLong()
        val requestUserId = chatLong(
            requireArguments().getString("requestUserId")!!.replace("{requestUserId=", "")
        ).toLong()
        initAdapter(noteId, productPostId)
        viewModel.makeId(creatingUserId, requestUserId, opponentUserId)
        binding.ivChatSend.setOnClickListener {
            val inputText = binding.etvChatSend.text.toString()
            val chatContent = viewModel.chatContent.value
            val imageMultipartBody = mutableListOf<MultipartBody.Part>()
            Log.d("image", list.toString())
            for (image in list) {
                val file = ImageRealPath(requireContext()).getFileFromUri(image)
                if (!file.exists()) file.mkdirs()
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                val body =
                    MultipartBody.Part.createFormData("multipartFileList", file.name, requestFile)
                imageMultipartBody.add(body)
            }
            val noteId1 = getBody("noteId", noteId)
            val senderId1 = getBody("senderId", chatContent!![0])
            val receiverId = getBody("receiverId", chatContent!![1])
            viewModel.sendFile(noteId1,senderId1,receiverId, imageMultipartBody)
//            viewModel.sendChat(Chat(noteId, inputText, chatContent!![0], chatContent!![1]))

        }
        binding.ivChatFile.setOnClickListener {
            selectGallery()
        }
        binding.tvDealproductGo.setOnClickListener {
            openDealDetail(productPostId)
        }

    }

    private fun initAdapter(noteId: Long, productPostId: Long): ChatAdapter {
        val pagingAdapter = ChatAdapter(viewModel)
        binding.rvChat.adapter = pagingAdapter
        productSubmitData(pagingAdapter, viewModel.getChatRoom(noteId))
        viewModel.detailProduct(productPostId)
        viewModel.dealProduct2.observe(viewLifecycleOwner) {
            binding.dealproduct = it
            loadImage( binding.ivDealProduct, it.imageLink)
            Log.d("dealProduct",it.imageLink)
        }
        binding.srlChatting.setOnRefreshListener {
            binding.srlChatting.isRefreshing=false
            productSubmitData(pagingAdapter, viewModel.getChatRoom(noteId))
        }
        setNavigation()
        return pagingAdapter
    }


    private fun productSubmitData(
        pagingAdapter: ChatAdapter,
        getData: Flow<PagingData<ChatRoom>>
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    getData.collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

    private fun openDealDetail(productPostId: Long) {
        findNavController().navigate(
            R.id.action_chatFragment_to_dealDetailFragment2, bundleOf(
                "productPostId" to productPostId
            )
        )
    }

    private fun setNavigation() {
        binding.toolbarChat.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


    /***
     * 리팩토링 필수 일단 기능구현 테스트
     *
     *
     * */
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            list.clear()
            if (result.data?.clipData != null) { // 사진 여러개 선택한 경우
                val count = result.data!!.clipData!!.itemCount
                if (count > 2) {
                    // Do something
                }
                for (i in 0 until count) {
                    val imageUri = result.data!!.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }
            } else {
                result.data?.data?.let { uri ->
                    val imageUri: Uri? = result.data?.data
                    if (imageUri != null) {
                        list.add(imageUri)
                    }
                }
            }
            binding.imageUrl = list[0].toString()
        }
    }


    private fun getBody(key: String, value: Any): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value.toString())
    }

    private fun selectGallery() {
        val writePermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        val readPermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        if (writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1
            )
        } else {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
            )

            imageResult.launch(intent)
        }
    }
}