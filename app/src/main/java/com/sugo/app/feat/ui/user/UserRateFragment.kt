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
import com.sugo.app.R
import com.sugo.app.databinding.FragmentDealDetailBinding
import com.sugo.app.databinding.FragmentUserRatingBinding
import com.sugo.app.feat.model.request.MannerTarget
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.dealdetail.DealDetailAdapter
import com.sugo.app.feat.ui.dealdetail.DetailViewModel
import com.sugo.app.feat.ui.upload.UploadViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class UserRateFragment : Fragment() {
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
        Log.d("test", userId.toString())
        viewModel.getUser(userId)
        viewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }
        var test: BigDecimal = BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP)
        binding.rgRate.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.cb_a -> test = BigDecimal("4.50").setScale(2, RoundingMode.HALF_UP)

                R.id.cb_b -> test = BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP)

                R.id.cb_c -> test = BigDecimal("2.50").setScale(2, RoundingMode.HALF_UP)

                R.id.cb_d -> test = BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP)
            }
        }
        setNavigation()

            binding.btnRating.setOnClickListener {
                Log.d("test","${MannerTarget(userId,test)}")
                viewModel.setManner(MannerTarget(userId, test))
            }

    }


    private fun setNavigation() {
        binding.toolbarProductDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}