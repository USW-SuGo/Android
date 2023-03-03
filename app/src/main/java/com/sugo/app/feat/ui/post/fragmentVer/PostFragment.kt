package com.sugo.app.feat.ui.post.fragmentVer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sugo.app.databinding.FragmentPostBinding

class PostFragment: Fragment() {

    private lateinit var binding: FragmentPostBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PostAdapter()
        binding.selectPicture.setOnClickListener {
        }
        binding.addrecycle.adapter=adapter
        adapter.submitList(listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKZC98XEJ4dZAccryBQ9mJARgxFOeUvYcR7vPPUH6DoQ&s","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpzflepfqRGsH0cBYKnXDVYovu_rKfIMU8TJpZbTZddpBtEzoRNyXKuv4OeElPqPGc0MY&usqp=CAU"))
    }
}
