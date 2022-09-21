package com.example.sugo_mvc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.sugo_mvc.databinding.ActivityMainBinding

import com.example.sugo_mvc.deal.DealFragment
import com.example.sugo_mvc.ui.IntroActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var container = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bottomBar = binding.bottomNav
        val toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //왼쪽 버튼 사용설정(기본은 뒤로가기)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //왼쪽 버튼 아이콘 변경
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_launcher_background)

        container = binding.mainContainer.id
        supportFragmentManager.beginTransaction().replace(container, DealFragment())
            .commitAllowingStateLoss()

        bottomBar.setOnItemSelectedListener {
            getFragment(it)
        }
        bottomBar.setOnItemReselectedListener {
            Log.d("Main","menu reselected")
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                //뒤로가기 버튼 눌렀을 때
                Log.d("ToolBar_item: ", "뒤로가기 버튼 클릭")
                finish()
                return true
            }
            R.id.toolbar_msg -> {

                Log.d("ToolBar_item: ", "msg버튼클릭")
                val intent = Intent(applicationContext, IntroActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.toolbar_search -> {
                Log.d("ToolBar_item: ", "검색버튼클릭")
                val intent = Intent(applicationContext, IntroActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun getFragment(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.menuDeal -> {
                supportFragmentManager.beginTransaction().replace(container,DealFragment())
                    .commitAllowingStateLoss()
            }
            else -> {
                supportFragmentManager.beginTransaction().replace(container, DealFragment())
                    .commitAllowingStateLoss()
            }
        }
        return true
    }
}