package com.sugo.app.feat.ui.deal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sugo.app.databinding.FragmentDealBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.deal.paging.ProductPagingAdapter
import com.sugo.app.feat.ui.deal.paging.ProductPagingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class DealFragment : Fragment(){


    private val viewModel: ProductPagingViewModel by viewModels { ViewModelFactory(requireContext()) }
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

        val pagingAdapter = ProductPagingAdapter()
        binding.rvDealProduct.adapter = pagingAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getMainPage().collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }
}

//class DealFragment : Fragment(){
//
//
//    private val viewModel: DealProductViewModel by viewModels { ViewModelFactory(requireContext()) }
//    private lateinit var binding: FragmentDealBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View{
//        binding = FragmentDealBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val productAdapter = ProductAdapter(viewModel)
//        binding.rvDealProduct.adapter = productAdapter
//        viewModel.items.observe(viewLifecycleOwner) {
//            productAdapter.submitList(it)
//        }
//    }
//}