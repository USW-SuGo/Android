package com.example.sugo_mvc.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.sugo_mvc.R
import com.example.sugo_mvc.databinding.FragmentDealBinding
import com.example.sugo_mvc.databinding.FragmentMapBinding
import java.net.Socket

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var mScaleGestureDetector:ScaleGestureDetector?=null
    private var scaleFactor=1.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapimage.setImage(ImageSource.resource(R.drawable.campusmap_img))

    }


}