package com.sugo.app.feat.ui.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sugo.app.databinding.MypageCheckDialogBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import kotlinx.coroutines.*

class CheckDialog(context: Context,val id:Long) : DialogFragment() {

    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }
    private val mContext: Context = context
    private lateinit var binding: MypageCheckDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MypageCheckDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOk.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val job = async {
                    viewModel.deletePost(id)
                }
                job.await()
                delay(500)
                dismiss()
            }
        }

        binding.btnCansel.setOnClickListener {
            dismiss()
        }
    }
}