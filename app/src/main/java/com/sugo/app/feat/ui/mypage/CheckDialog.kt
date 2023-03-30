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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface CheckDialogListener {
    fun onCheckDialogResult()
}

class CheckDialog(context: Context, val id: Long, val type: String) : DialogFragment() {

    private var listener: CheckDialogListener? = null
    fun setCheckDialogListener(listener: CheckDialogListener) {
        this.listener = listener
    }
    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }
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

        if (type == "delete") {
            binding.tvIntro.text = "삭제하시겠습니까?"
            binding.btnOk.setOnClickListener { handleClick() }
        } else {
            binding.tvIntro.text = "판매완료로 하시겠습니까?"
            binding.btnOk.setOnClickListener { handleClick() }
        }
        binding.btnCansel.setOnClickListener {
            dismiss()
        }
    }

    private fun handleClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            val job = async {
                when (type) {
                    "delete" -> {viewModel.deletePost(id)}
                    else -> viewModel.postClose(id)
                }
            }
            job.await()
            delay(500)
            dismiss()
            listener?.onCheckDialogResult()
        }
    }
}