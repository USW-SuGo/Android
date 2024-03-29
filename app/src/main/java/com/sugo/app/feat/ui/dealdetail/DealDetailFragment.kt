package com.sugo.app.feat.ui.dealdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.sugo.app.R
import com.sugo.app.databinding.FragmentDealDetailBinding
import com.sugo.app.feat.ui.common.ViewModelFactory

class DealDetailFragment : Fragment() {

    private lateinit var binding: FragmentDealDetailBinding
    private val viewModel: DetailViewModel by viewModels { ViewModelFactory(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDealDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        val productId = requireArguments().getLong("productPostId")
        val adapter = initAdapter()
        viewModel.likeStatus.observe(viewLifecycleOwner) {
            binding.ivHeart.isSelected = it?:false
        }
        var userId:Long=0
        viewModel.dealProduct.observe(viewLifecycleOwner) {
            userId=it.writerId
        }

        binding.tvNickname.setOnClickListener{
            openUser(userId)
        }
        setViewPagerAdapter(adapter, productId)
        setNavigation()
    }

    private fun initAdapter(): DealDetailAdapter {
        val adapter = DealDetailAdapter()
        binding.viewpagerDealDetail.adapter = adapter
        return adapter
    }

    private fun setViewPagerAdapter(adapter: DealDetailAdapter, productId: Long) {
        with(binding.viewpagerDealDetail) {
            setPageTransformer { page, position ->
                page.translationX = position * -10
            }
            TabLayoutMediator(binding.viewpagerDealDetailIndicator, this) { tab, position ->
            }.attach()
        }
        viewModel.detailProduct(productId)
        viewModel.dealProduct.observe(viewLifecycleOwner) {
            binding.dealproduct = it
            val imageLinkList = it.imageLink.split(", ").toMutableList()
            adapter.submitList(imageLinkList)
        }
    }
    private fun openUser(userId: Long) {
        findNavController().navigate(
            R.id.action_dealDetailFragment_to_userFragment, bundleOf(
                "userId" to userId
            )
        )
    }
    private fun setNavigation() {
        binding.toolbarProductDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}