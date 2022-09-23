package com.example.sugo_mvc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

import com.example.sugo_mvc.R
import com.example.sugo_mvc.databinding.ActivityDealDetailBinding
import com.example.sugo_mvc.databinding.ActivityMainBinding
import com.example.sugo_mvc.ui.deal.DealViewPagerAdapter

class DealDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDealDetailBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.dealViewPager.adapter=DealViewPagerAdapter(getAespaMembers())
        binding.dealViewPager.orientation=ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
    }

    // 뷰 페이저에 들어갈 아이템
    private fun getAespaMembers(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.ic_launcher_background,
            R.drawable.search,
            R.drawable.plus,
            R.drawable.user)
    }
}