package com.sugo.app.feat.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.sugo.app.databinding.FragmentUserBinding
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.mypage.MyPageAdapter
import com.sugo.app.feat.ui.mypage.MyPageViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserFragment : Fragment() {
    private val viewModel: UserViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val userId = requireArguments().getLong("userId")
        userSet(userId)
        setNavigation()
        val pagingAdapter = initAdapter()
        binding.tvUserWrite.setOnClickListener {
            productSubmitData(pagingAdapter, viewModel.getUserProdcut(userId))
        }
        binding.tvLikeWrite.setOnClickListener {
            productSubmitData(pagingAdapter, viewModel.getUserCompleteProduct(userId))
        }

    }
    private fun initAdapter(): UserAdapter {
        val pagingAdapter = UserAdapter(viewModel)
        binding.rvMypage.adapter = pagingAdapter
        return pagingAdapter
    }

    private fun productSubmitData(
        pagingAdapter: UserAdapter,
        getData: Flow<PagingData<DealProduct>>
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    getData.collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }
    private fun userSet(userId: Long) {
        viewModel.getUser(userId)
        viewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }
    }


    private fun setNavigation() {
        binding.toolbarProductDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}