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
import com.sugo.app.feat.model.request.Department
import com.sugo.app.feat.model.request.UserSign
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
    private var id :Long = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel=viewModel
        val productId = requireArguments().get("userSign")
        Log.d("SelectMajorFragment",productId.toString())
        val a = productId.toString().split(",")
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

        // 문제점이 많다 클릭이벤트 여기서 하면 안돼잉 해야지삭아
        //기능 테스트
        binding.btnNextgo.setOnClickListener{
            viewModel.join(UserSign(a[1],"${a[0].replace("[","")}@suwon.ac.kr",a[2].replace("]",""),viewModel.department.value!!))
            viewModel.id.observe(viewLifecycleOwner){
                Log.d("  binding.test = it",viewModel.id.value!!.id.toString())
                id=viewModel.id.value!!.id
            }
            viewModel.openAuthNum()
        }
        setNavigation()


    }

    private fun setNavigation() {
        viewModel.id.observe(viewLifecycleOwner){
            Log.d("  binding.test = it",viewModel.id.value!!.id.toString())
            id=viewModel.id.value!!.id
            openSelect(id)
        }
//        viewModel.openAuthNum.observe(viewLifecycleOwner, EventObserver {
//            Log.d("AuthNum",it.toString())
//            openSelect(id)
//        })
    }

    private fun openSelect(id:Long) {
        findNavController().navigate(
            R.id.action_selectMajorFragment_to_emailAuthFragment, bundleOf(
                "userSign" to id
            )
        )
    }
}
