package com.sugo.app.feat.ui.mypage

import android.os.Bundle
import android.util.Log
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
import com.sugo.app.databinding.FragmentMypageBinding
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.deal.ProductPagingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyPageFragment : Fragment() {
    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentMypageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUser()
        val pagingAdapter = initAdapter()
        productSubmitData(pagingAdapter)
        setNavigation()
    }

    private fun initAdapter(): MyPageAdapter {

        val pagingAdapter = MyPageAdapter(viewModel)
        binding.rvMypage.adapter = pagingAdapter
        return pagingAdapter
    }

    private fun setUser() {
        viewModel.myPage.observe(viewLifecycleOwner) {
            binding.mypage = it
        }
    }

    private fun setNavigation() {
        viewModel.openDealEvent.observe(viewLifecycleOwner, EventObserver {
            Log.d("productPostId", it.toString())
            openDealDetail(it)
        })
    }

    private fun openDealDetail(productPostId: Long) {
        findNavController().navigate(
            R.id.action_mypage_to_dealDetail, bundleOf(
                "productPostId" to productPostId
            )
        )
    }

    private fun productSubmitData(pagingAdapter: MyPageAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getMyPageProduct().collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

}