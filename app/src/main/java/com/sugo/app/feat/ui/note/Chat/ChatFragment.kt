package com.sugo.app.feat.ui.note.Chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.sugo.app.databinding.FragmentChattingBinding
import com.sugo.app.databinding.FragmentMessageBinding
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.mypage.MyPageViewModel
import com.sugo.app.feat.ui.note.MessageAdapter
import com.sugo.app.feat.ui.note.MessageViewModel
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
        val noteId = requireArguments().getString("noteId")
        initAdapter(noteId!!.substringBefore(".").toLong())
        setNavigation()
    }
    private fun initAdapter(noteId:Long): ChatAdapter {

        val pagingAdapter = ChatAdapter(viewModel)
        binding.rvChat.adapter = pagingAdapter
        productSubmitData(pagingAdapter, viewModel.getChatRoom(noteId))
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
    private fun setNavigation() {
        binding.toolbarChat.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}