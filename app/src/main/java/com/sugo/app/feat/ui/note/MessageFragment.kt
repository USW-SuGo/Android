package com.sugo.app.feat.ui.note


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
import com.sugo.app.databinding.FragmentMessageBinding
import com.sugo.app.feat.model.ProductPostId
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.ui.common.EventObserver

import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.mypage.MyPageViewModel
import com.sugo.app.feat.ui.note.Chat.ChatViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessageFragment : Fragment() {
    private val viewModel: MessageViewModel by viewModels { ViewModelFactory(requireContext()) }
    private val viewModel2: ChatViewModel by viewModels { ViewModelFactory(requireContext()) }

    private lateinit var binding: FragmentMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation()
        initAdapter()
    }

    private fun initAdapter(): MessageAdapter {

        val pagingAdapter = MessageAdapter(viewModel, viewModel2)
        binding.rvNote.adapter = pagingAdapter
        productSubmitData(pagingAdapter, viewModel.getNoteRoom())
        return pagingAdapter
    }

    private fun setNavigation() {

        viewModel.openChatEvent.observe(viewLifecycleOwner, EventObserver {
            Log.d("productPostId", it.toString())
            openChat(it[0], it[1], it[2], it[3], it[4])
        })
    }

    private fun openChat(
        noteId: String,
        productPostId: String,
        creatingUserId: String,
        opponentUserId: String,
        requestUserId: String
    ) {
        findNavController().navigate(
            R.id.action_navigation_message_to_chatFragment, bundleOf(
                "noteId" to noteId,
                "productPostId" to productPostId,
                "creatingUserId" to creatingUserId,
                "opponentUserId" to opponentUserId,
                "requestUserId" to requestUserId
            )
        )
    }

    private fun productSubmitData(
        pagingAdapter: MessageAdapter,
        getData: Flow<PagingData<NoteContent>>
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
}
