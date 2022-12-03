package com.example.sugo_mvc.ui.deal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.sugo_mvc.databinding.ActivityImageBinding


class ImageActivity : AppCompatActivity() {
    val binding by lazy { ActivityImageBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imagelink = intent.getStringExtra("pos")
        val imageList = imagelink!!.split(",")
        Log.d("image",imageList.toString())
        binding.dealViewPager2.adapter = DealViewPagerAdapter2(imageList)
        binding.dealViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }
}