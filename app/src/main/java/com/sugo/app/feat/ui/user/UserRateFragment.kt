package com.sugo.app.feat.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.sugo.app.databinding.FragmentDealDetailBinding
import com.sugo.app.databinding.FragmentUserRatingBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.dealdetail.DealDetailAdapter
import com.sugo.app.feat.ui.dealdetail.DetailViewModel
import com.sugo.app.feat.ui.upload.UploadViewModel

class UserRateFragment: Fragment() {
    private val viewModel: UserViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentUserRatingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        val userId = requireArguments().getLong("userId")
        Log.d("test",userId.toString())
        viewModel.getUser(userId)
        viewModel.user.observe(viewLifecycleOwner){
            binding.user=it
        }
        setNavigation()
    }



    private fun setNavigation() {
        binding.toolbarProductDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}