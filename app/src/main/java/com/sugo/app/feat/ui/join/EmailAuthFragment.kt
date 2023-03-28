package com.sugo.app.feat.ui.join

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sugo.app.databinding.FragmentEmailAuthBinding
import com.sugo.app.feat.model.request.PayLoad
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.join.inputUser.JoinViewModel

class EmailAuthFragment : Fragment() {
    private val viewModel: JoinViewModel by viewModels { ViewModelFactory(requireContext()) }

    private lateinit var binding: FragmentEmailAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmailAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = requireArguments().getLong("userSign")
        Log.d("productId",productId.toString())
        binding.viewmodel=viewModel
        binding.btnJoin.setOnClickListener {
            viewModel.checkPayLoad(PayLoad(productId,binding.etvAuthNum.text.toString()))
        }
    }

}