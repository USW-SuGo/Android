package com.sugo.app.feat.ui.mypage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
        //이부분을  VM 으로 옮기기 뷰엣허 클릭시 센변경을 하는방법을 모르곘네  셀렉터인가 아닌가?
        binding.tvUserWrite.setOnClickListener {
            productSubmitData(pagingAdapter)
            binding.tvUserWrite.setTextColor(Color.parseColor("#000000"))
            binding.tvLikeWrite.setTextColor(Color.parseColor("#419e3a"))
        }
        binding.tvLikeWrite.setOnClickListener {
            productLikeSubmitData(pagingAdapter)
            binding.tvUserWrite.setTextColor(Color.parseColor("#419e3a"))
            binding.tvLikeWrite.setTextColor(Color.parseColor("#000000"))
        }
        openDailog()
        setNavigation()
    }

    private fun openDailog() {
        viewModel.openDealEvent1.observe(viewLifecycleOwner, EventObserver {
            val bottomSheetFragment = CustomDialog(requireContext(), it)
            Log.d("productPostId", it.toString())
            bottomSheetFragment.show(childFragmentManager, "childFragmentManager")
        })
    }

    private fun initAdapter(): MyPageAdapter {
        val pagingAdapter = MyPageAdapter(viewModel)
        binding.rvMypage.adapter = pagingAdapter
        productSubmitData(pagingAdapter)
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

    private fun productLikeSubmitData(pagingAdapter: MyPageAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getLikeProduct().collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

}