package com.sugo.app.feat.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sugo.app.databinding.FragmentMessageBinding
import com.sugo.app.feat.ui.login.LoginActivity

class MessageFragment : Fragment() {
//    private val viewModel: LoginViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
