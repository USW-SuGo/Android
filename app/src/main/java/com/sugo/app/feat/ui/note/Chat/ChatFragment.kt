package com.sugo.app.feat.ui.note.Chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sugo.app.databinding.FragmentChattingBinding
import com.sugo.app.databinding.FragmentMessageBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.mypage.MyPageViewModel
import com.sugo.app.feat.ui.note.MessageViewModel

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
        val productId = requireArguments().getString("noteId")
        viewModel.getChatRoom()
        Log.d("test",productId!!.substringBefore(".").toLong().toString())
        setNavigation()
    }

    private fun setNavigation() {
        binding.toolbarChat.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}