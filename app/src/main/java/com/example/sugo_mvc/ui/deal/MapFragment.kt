package com.example.sugo_mvc.ui.deal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sugo_mvc.R
import com.example.sugo_mvc.databinding.FragmentDealBinding
import com.example.sugo_mvc.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!


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

}