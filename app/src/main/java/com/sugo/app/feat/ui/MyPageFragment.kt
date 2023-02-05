package com.sugo.app.feat.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sugo.app.databinding.FragmentLoginBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.login.LoginViewModel

class MyPageFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginviewmodel = viewModel
        Log.d("Test", stringPreferencesKey("access_token").toString())

    }


}