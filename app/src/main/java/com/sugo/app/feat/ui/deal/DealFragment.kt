package com.sugo.app.feat.ui.deal

import android.content.Intent
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
import com.sugo.app.feat.App
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.login.LoginActivity
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
        Log.d("DealFragment",App.loginform.value.toString())
        productSubmitData(pagingAdapter,viewModel.getMainPage())
        setNavigation()
    }

    private fun initAdapter(): ProductPagingAdapter {
        val pagingAdapter = ProductPagingAdapter(viewModel)
        binding.rvDealProduct.adapter = pagingAdapter
        return pagingAdapter
    }

    // 토큰이 만료되었으면
    private fun setNavigation() {
        viewModel.openDealEvent.observe(viewLifecycleOwner, EventObserver {
            Log.d("productPostId",it.toString())
            if (App.loginform.value == false) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            } else {
                openDealDetail(it)
            }
            Log.d("productPostId",App.loginform.value.toString())

        })
    }

    private fun openDealDetail(productPostId:Long) {
        findNavController().navigate(R.id.action_deal_to_deal_detail, bundleOf(
            "productPostId" to productPostId
        ))
    }
    /**
    리프레쉬 토큰이 만료가 되고 들어가는 경우
    이경우에만 들어가지지 않는다
    만료가 되지않고 들어가는 경우**/
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
