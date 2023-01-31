package com.sugo.app.feat.ui.dealdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sugo.app.databinding.FragmentDealBinding
import com.sugo.app.databinding.FragmentDealDetailBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.deal.ProductPagingViewModel

class DealDetailFragment: Fragment(){

    private lateinit var binding: FragmentDealDetailBinding

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
    }
}