package com.example.sugo_mvc

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sugo_mvc.databinding.ActivityMainBinding
import com.example.sugo_mvc.ui.*

import com.example.sugo_mvc.ui.deal.DealFragment

import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var container = -1
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bottomBar = binding.bottomNav
        val toolbar=binding.toolbar
        val drawerBar=binding.navigationView
        drawerLayout=binding.mainDrawerLayout

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //왼쪽 버튼 사용설정(기본은 뒤로가기)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //왼쪽 버튼 아이콘 변경
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.hambug)
        toolbar.setBackgroundColor(Color.rgb(102,255,102));

        drawerBar.setNavigationItemSelectedListener(this)
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

                drawerLayout.openDrawer(GravityCompat.START)

                return true
            }
            R.id.toolbar_msg -> {
                Log.d("ToolBar_item: ", "msg버튼클릭")
                val intent = Intent(applicationContext, IntroActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.additem -> {

                Log.d("ToolBar_item: ", "msg버튼클릭")
                val intent = Intent(applicationContext, AddItemActivity::class.java)
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
                supportFragmentManager.beginTransaction().replace(container, DealFragment())
                    .commitAllowingStateLoss()
            }
            R.id.menumap -> {
                supportFragmentManager.beginTransaction().replace(container, MapFragment())
                    .commitAllowingStateLoss()
            }
            else -> {
                supportFragmentManager.beginTransaction().replace(container, DealFragment())
                    .commitAllowingStateLoss()
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuDeal1 -> {
                Toast.makeText(this, "menu_item1 실행", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.menumap1 -> {
                Toast.makeText(this, "menu_item2 실행", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, JoinActivity::class.java)
                startActivity(intent)
            }
            R.id.menuMyPage1 -> Toast.makeText(this, "menu_item3 실행", Toast.LENGTH_SHORT).show()
        }
        return false
    }

}