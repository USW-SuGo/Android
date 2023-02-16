package com.sugo.app.feat.ui.join

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sugo.app.databinding.FragmentJoinBinding
import com.sugo.app.databinding.FragmentLoginBinding
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.login.LoginViewModel

class JoinInputFragment : Fragment() {
    private val viewModel: JoinViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentJoinBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        IntroChange()

    }

    private fun IntroChange() {
        viewModel.introid.observe(viewLifecycleOwner) {
            Log.d("tvIdintro", it)
            binding.tvCheckIdIntro.text = it
        }
        viewModel.introEmail.observe(viewLifecycleOwner) {
            Log.d("tvEmailintro", it)
            binding.tvEmailIntro.text = it
        }
    }
}