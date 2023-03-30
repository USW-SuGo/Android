package com.sugo.app.feat.ui.upload

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sugo.app.R
import com.sugo.app.databinding.FragmentSettingBinding
import com.sugo.app.databinding.FragmentUploadBinding
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.User
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.deal.ProductPagingViewModel
import com.sugo.app.feat.ui.login.LoginActivity

class UploadFragment: Fragment() {
    private val viewModel: UploadViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentUploadBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel=viewModel

        viewModel.openContactEvent.observe(viewLifecycleOwner,EventObserver{
            openContact()
        })
    }
    private fun openContact() {
        findNavController().navigate(R.id.action_upload_to_contact, bundleOf())
    }

}