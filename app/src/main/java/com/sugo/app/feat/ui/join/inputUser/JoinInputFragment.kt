package com.sugo.app.feat.ui.join.inputUser

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
import com.sugo.app.databinding.FragmentJoinBinding
import com.sugo.app.feat.model.UserSign
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.ViewModelFactory

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
        setNavigation()

    }

    private fun setNavigation() {
        viewModel.openMajorEvent.observe(viewLifecycleOwner, EventObserver {
            Log.d("JoinInput", it.toString())
            openSelect(it)
        })
    }

    private fun openSelect(userSign: List<String>) {
        findNavController().navigate(
            R.id.action_joinInputFragment_to_selectMajorFragment, bundleOf(
                "userSign" to userSign
            )
        )
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
        viewModel.introPwd.observe(viewLifecycleOwner) {
            Log.d("tvPwdintro", it)
            binding.tvCheckPwdIntro.text = it
        }
    }
}
