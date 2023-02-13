package com.sugo.app.feat.ui.mypage

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sugo.app.R
import com.sugo.app.databinding.FragmentMypageBinding
import com.sugo.app.databinding.MypageProductDialogBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class CustomDialog(context: Context, val id: Long) : BottomSheetDialogFragment() {
    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }
    private val mContext: Context = context
    private lateinit var binding: MypageProductDialogBinding
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


        binding.tvDealComplete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val job = async {
                    val bottomSheetFragment = CheckDialog(requireContext(), id)
                    bottomSheetFragment.show(parentFragmentManager, "childFragmentManager")
                }
                job.await()
                dismiss()
            }
        }

        binding.tvDealDelete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val job = async {
                    val bottomSheetFragment = CheckDialog(requireContext(), id)
                    bottomSheetFragment.show(parentFragmentManager, "childFragmentManager")
                }
                job.await()
                dismiss()
            }
        }
    }
}
