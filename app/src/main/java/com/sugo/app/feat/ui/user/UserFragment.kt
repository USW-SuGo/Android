package com.sugo.app.feat.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sugo.app.databinding.FragmentUserBinding
import com.sugo.app.feat.ui.common.ViewModelFactory

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