package com.sugo.app.feat.ui.deal

import android.os.Bundle
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
import com.sugo.app.R
import com.sugo.app.databinding.FragmentDealBinding
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.ViewModelFactory
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

        val pagingAdapter = initAdapter()
        productSubmitData(pagingAdapter)
        setNavigation()
    }

    private fun initAdapter(): ProductPagingAdapter {
        val pagingAdapter = ProductPagingAdapter(viewModel)
        binding.rvDealProduct.adapter = pagingAdapter
        return pagingAdapter
    }

    private fun setNavigation(){
        viewModel.openDealEvent.observe(viewLifecycleOwner,EventObserver{
            openDealDetail()
        })
    }

    private fun openDealDetail(){
        findNavController().navigate(R.id.action_deal_to_deal_detail, bundleOf())
    }
    private fun productSubmitData(pagingAdapter: ProductPagingAdapter) {
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
