package com.sugo.app.feat.ui.upload

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.sugo.app.databinding.FragmentContactBinding
import com.sugo.app.feat.model.place
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.join.AssetLoader

class BottomSheetContact(context: Context) : BottomSheetDialogFragment() {

    private lateinit var onClickListner: bottomSheetContactListner
    private lateinit var binding: FragmentContactBinding
    private val viewModel: UploadViewModel by viewModels { ViewModelFactory(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val asserLoader = AssetLoader(requireContext())
        val placeAssetLoader = asserLoader.getJsonString("place.json")
        val gson = Gson()
        val placeAsset = gson.fromJson(placeAssetLoader, place::class.java)

        binding.contactrv.adapter = ContactAdapter(viewModel).apply {
            submitList(placeAsset.contactPlace)
        }
        viewModel.place.observe(viewLifecycleOwner) {
            onClickListner.onClicked(it)
            dismiss()
        }
    }

    fun setOnClickListner(listner: bottomSheetContactListner) {
        onClickListner = listner
    }

    interface bottomSheetContactListner {
        fun onClicked(clickItem: String)
    }

}