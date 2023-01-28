package com.sugo.app.feat.ui.deal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sugo.app.databinding.FragmentDealBinding
import com.sugo.app.feat.ui.common.ViewModelFactory

class DealFragment : Fragment(){


    private val viewModel: DealProductViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentDealBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDealBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productAdapter = ProductAdapter(viewModel)
        binding.rvDealProduct.adapter = productAdapter
        viewModel.items.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
        }
    }
}