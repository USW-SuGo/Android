package com.sugo.app.feat.ui.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sugo.app.databinding.MypageProductDialogBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
interface BottomSheetListner {
    fun onBottomSheetResult()
}

class BottomSheetDialog(context: Context, val id: Long) : BottomSheetDialogFragment(),
    CheckDialogListener {

    private var checkDialogListener: CheckDialogListener? = null
    private var bottomSheetListner: BottomSheetListner? = null
    private lateinit var binding: MypageProductDialogBinding
    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MypageProductDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick(binding.tvDealComplete, "deal")
        onClick(binding.tvDealDelete,"delete")
    }

    fun setCheckDialogListener2(listener: BottomSheetListner) {
        this.bottomSheetListner= listener
    }
    private fun onClick(textView: TextView, type: String) {
        textView.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val job = async {
                    val bottomSheetFragment = CheckDialog(requireContext(), id, type)
                    bottomSheetFragment.setCheckDialogListener(this@BottomSheetDialog)
                    bottomSheetFragment.show(parentFragmentManager, "childFragmentManager")
                }
                job.await()
                dismiss()
                checkDialogListener?.onCheckDialogResult()
            }
        }
    }

    override fun onCheckDialogResult() {
        bottomSheetListner?.onBottomSheetResult()
    }

}