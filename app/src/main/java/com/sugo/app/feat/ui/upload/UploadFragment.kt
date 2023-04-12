package com.sugo.app.feat.ui.upload

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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sugo.app.databinding.FragmentUploadBinding
import com.sugo.app.feat.model.ProductPostId
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.ui.MainActivity
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.ImageRealPath
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.common.hideKeyboard
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class UploadFragment : Fragment() {
    private val viewModel: UploadViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentUploadBinding
    private val list = ArrayList<Uri>()
    private val adapter = UploadAdapter(list)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     *
     * 미래의 삭아 고생하자
     * **/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        val category = listOf("서적", "전자기기", "생활용품", "기타")
        viewModel.setSpinnerEntry(category)
        binding.loUpload.setOnClickListener {
            this@UploadFragment.hideKeyboard()
        }
        binding.selectPicture.setOnClickListener {
            selectGallery()
        }
        binding.addrecycle.adapter = adapter

        binding.addsugo.setOnClickListener {
            val title = getBody("title", binding.postTitle.text.toString())
            val content = getBody("content", binding.postContent.text.toString())
            val price = getBody("price", binding.postPrice.editableText.toString())
            val contactPlace = getBody("contactPlace", "중앙도서관")
            var a = ""
            lifecycleScope.launch {
                viewModel.spinnerData.collect {
                    Log.v("sdfsdfsdf", it)
                    a = it
                }
            }
            val postCategory = getBody("category", a)

            val imageMultipartBody = mutableListOf<MultipartBody.Part>()
            for (image in list) {
                val file = ImageRealPath(requireContext()).getFileFromUri(image)
                if (!file.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                    file.mkdirs()
                }
                val requestFile = RequestBody.create("image*/".toMediaTypeOrNull(), file)
                val body =
                    MultipartBody.Part.createFormData("multipartFileList", file.name, requestFile)
                imageMultipartBody.add(body)
            }

            SugoRetrofit.tokenCreate().postUpload(
                title, content, price, contactPlace, postCategory, imageMultipartBody
            )
                .enqueue(object : retrofit2.Callback<ProductPostId> {
                    override fun onResponse(
                        call: Call<ProductPostId>,
                        response: Response<ProductPostId>
                    ) {
                        if (response.isSuccessful) {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            Log.d("postUploadFail", response.errorBody()!!.toString())
                        }
                    }

                    override fun onFailure(call: Call<ProductPostId>, t: Throwable) {
                        Log.d("postUploadFail", t.toString())
                    }

                })
        }
        openDailog()
    }

    private fun openDailog() {
        viewModel.openContactEvent.observe(viewLifecycleOwner, EventObserver {
            val bottomSheetFragment = BottomSheetContact(requireContext())
            bottomSheetFragment.show(parentFragmentManager, "childFragmentManager")
            bottomSheetFragment.setOnClickListner(object :
                BottomSheetContact.bottomSheetContactListner {
                override fun onClicked(clickItem: String) {
                    binding.postContactPlace.text = clickItem
                }
            })
        })
    }


    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            list.clear()
            if (it.data?.clipData != null) { // 사진 여러개 선택한 경우
                val count = it.data!!.clipData!!.itemCount
                if (count > 2) {
                    Toast.makeText(requireContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                }
                for (i in 0 until count) {
                    val imageUri = it.data!!.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }
            } else {
                it.data?.data?.let { uri ->
                    val imageUri: Uri? = it.data?.data
                    if (imageUri != null) {
                        list.add(imageUri)
                    }
                }
            }
            adapter.notifyDataSetChanged()
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