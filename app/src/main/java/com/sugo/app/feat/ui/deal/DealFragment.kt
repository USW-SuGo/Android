package com.sugo.app.feat.ui.deal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.sugo.app.R
import com.sugo.app.databinding.FragmentDealBinding
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.ViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch


class DealFragment : Fragment() {

    private val viewModel: ProductPagingViewModel by viewModels { ViewModelFactory(requireContext()) }
    private val viewModel2: SearchPagingViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentDealBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productvm = viewModel2
        val pagingAdapter = initAdapter()
        viewModel2.searchValue.observe(viewLifecycleOwner) {
            productSubmitData2(pagingAdapter, it, "")
        }

//        lifecycleScope.launch {
//            viewModel2.spinnerData.collect {
//                Log.v("Spinner", it)
//                if (it =="전체"||it=="카테고리") productSubmitData2(pagingAdapter, "", "")
//                productSubmitData2(pagingAdapter,"",it)
//            }
//        }

        productSubmitData(pagingAdapter,viewModel.getMainPage())
        setNavigation()
    }

    private fun initAdapter(): ProductPagingAdapter {
        val pagingAdapter = ProductPagingAdapter(viewModel)
        binding.rvDealProduct.adapter = pagingAdapter
        return pagingAdapter
    }

    private fun setNavigation() {
        viewModel.openDealEvent.observe(viewLifecycleOwner, EventObserver {
            Log.d("productPostId",it.toString())
            openDealDetail(it)
        })
    }

    private fun openDealDetail(productPostId:Long) {
        findNavController().navigate(R.id.action_deal_to_deal_detail, bundleOf(
            "productPostId" to productPostId
        ))
    }

    private fun productSubmitData(pagingAdapter: ProductPagingAdapter,getMainData: Flow<PagingData<DealProduct>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    getMainData.collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

    private fun productSubmitData2(
        pagingAdapter: ProductPagingAdapter, value: String, category: String
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel2.getSearchPage(value, category).collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

}
