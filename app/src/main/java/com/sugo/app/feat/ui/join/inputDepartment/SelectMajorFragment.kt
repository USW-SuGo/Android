package com.sugo.app.feat.ui.join.inputDepartment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.sugo.app.R
import com.sugo.app.databinding.FragmentSelectMajorBinding
import com.sugo.app.feat.model.Department
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.join.AssetLoader
import com.sugo.app.feat.ui.join.inputUser.JoinViewModel

class SelectMajorFragment: Fragment() {
    private val viewModel: JoinViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentSelectMajorBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectMajorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel=viewModel

        val asserLoader = AssetLoader(requireContext())
        val departmentAssetLoader = asserLoader.getJsonString("department.json")
        if (!departmentAssetLoader.isNullOrEmpty()) {
            val gson = Gson()
            val departmentAsset = gson.fromJson(departmentAssetLoader, Department::class.java)
            binding.rvSelectDepartment.adapter = SelectAdapter(viewModel).apply {
                submitList(departmentAsset.departments)
            }
        }
        viewModel.department.observe(viewLifecycleOwner){
            binding.tvDepartmetCheck.text = it
            Log.d("  binding.tvDepartmetCheck.text = it",it.toString())
        }
        setNavigation()
    }

    private fun setNavigation() {
        viewModel.openAuthNum.observe(viewLifecycleOwner, EventObserver {
            Log.d("AuthNum",it.toString())
            openSelect(it)
        })
    }

    private fun openSelect(userSign:String) {
        findNavController().navigate(
            R.id.action_selectMajorFragment_to_emailAuthFragment, bundleOf(
                "userSign" to userSign
            )
        )
    }
}
