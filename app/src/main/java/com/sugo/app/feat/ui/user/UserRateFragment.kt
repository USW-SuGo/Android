package com.sugo.app.feat.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sugo.app.R
import com.sugo.app.databinding.FragmentUserRatingBinding
import com.sugo.app.feat.model.request.MannerTarget
import com.sugo.app.feat.ui.common.ViewModelFactory
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
        setUser(userId)
        var grade: BigDecimal = BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP)
        grade = checkRadio(grade)
        setNavigation()
        evaluateGrade(userId, grade)

    }

    private fun checkRadio(test: BigDecimal): BigDecimal {
        var test1 = test
        binding.rgRate.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.cb_a -> test1 = BigDecimal("4.50").setScale(2, RoundingMode.HALF_UP)

                R.id.cb_b -> test1 = BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP)

                R.id.cb_c -> test1 = BigDecimal("2.50").setScale(2, RoundingMode.HALF_UP)

                R.id.cb_d -> test1 = BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP)
            }
        }
        return test1
    }

    private fun evaluateGrade(userId: Long, test: BigDecimal) {
        binding.btnRating.setOnClickListener {
            viewModel.setManner(MannerTarget(userId, test))
        }
    }

    private fun setUser(userId: Long) {
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