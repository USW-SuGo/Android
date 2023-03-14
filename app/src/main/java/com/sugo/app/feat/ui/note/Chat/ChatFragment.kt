package com.sugo.app.feat.ui.note.Chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.common.chatLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModels { ViewModelFactory(requireContext()) }

    private lateinit var binding: FragmentChattingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteId = requireArguments().getString("noteId")!!.substringBefore(".").toLong()
        Log.d("noteId",noteId.toString())
        val productPostId = chatLong(requireArguments().getString("productPostId")!!).toLong()
        val creatingUserId = chatLong(requireArguments().getString("creatingUserId")!!).toLong()
        val opponentUserId = chatLong(requireArguments().getString("opponentUserId")!!).toLong()
        val requestUserId = chatLong(requireArguments().getString("requestUserId")!!.replace("{requestUserId=","")).toLong()
        initAdapter(noteId,productPostId)
            if (creatingUserId==requestUserId){
                viewModel.getTest(
                  creatingUserId,
                    opponentUserId)
            }
            else{
                viewModel.getTest(
                    creatingUserId,
                    opponentUserId)
            }
        binding.ivChatSend.setOnClickListener {
            val inputText = binding.etvChatSend.text.toString()
            val chatContent=viewModel.chatContent.value
            Log.d("chatContent","${chatContent!![0]},${chatContent[1]}")
            viewModel.sendChat(Chat(noteId,inputText,chatContent!![0],chatContent[1]))
        }
        binding.tvDealproductGo.setOnClickListener {
            openDealDetail(productPostId)
        }
    }
    private fun initAdapter(noteId:Long,productPostId:Long): ChatAdapter {
        val pagingAdapter = ChatAdapter(viewModel)
        binding.rvChat.adapter = pagingAdapter
        productSubmitData(pagingAdapter, viewModel.getChatRoom(noteId))
        viewModel.detailProduct(productPostId)
        viewModel.dealProduct2.observe(viewLifecycleOwner) {
            binding.dealproduct = it
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
    private fun openDealDetail(productPostId:Long) {
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
}