package com.example.sugo_mvc

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sugo_mvc.databinding.ActivityMainBinding
import com.example.sugo_mvc.ui.JoinActivity
import com.example.sugo_mvc.ui.MapFragment
import com.example.sugo_mvc.ui.adddealitem.AddItemActivity
import com.example.sugo_mvc.ui.deal.DealFragment
import com.example.sugo_mvc.ui.login.LoginActivity
import com.example.sugo_mvc.ui.messageRoom.MessageRoomActivity
import com.example.sugo_mvc.ui.user.UserPageActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var container = -1
    lateinit var drawerLayout: DrawerLayout

//    var accessToken = App.prefs.AccessToken!!.replace("AccessToken=", "")
//    val refreshToken=App.prefs.RefreshToken!!.replace("RefreshToken=", "")
    var a=0
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
        toolbar.setBackgroundColor(Color.rgb(255,255,255));
//        Log.d("token",accessToken+"\\"+refreshToken)
        drawerBar.setNavigationItemSelectedListener(this)
//        RetrofitBuilder.service.refresh(refreshToken).enqueue(object : retrofit2.Callback<Token>{
//            override fun onResponse(call: Call<Token>, response: Response<Token>) {
//
//                if (response.isSuccessful){
//                    Log.d("refresh1",response.body().toString())
//                    drawerBar.menu.setGroupVisible(R.id.menu_01, false)
//                    drawerBar.menu.setGroupVisible(R.id.menu_02,false)
//                }
//                else{
//                    Log.d("refresh2",response.errorBody().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<Token>, t: Throwable) {
//                Log.d("refresh3",t.toString())
//            }
//
//        })

        if (a==0) {
//            drawerBar.menu.setGroupVisible(R.id.menu_011, false)
            drawerBar.menu.setGroupVisible(R.id.menu_022, false)
        } else {
            drawerBar.menu.setGroupVisible(R.id.menu_01, true)
            drawerBar.menu.setGroupVisible(R.id.menu_02, true)
        }
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

//            R.id.toolbar_msg -> {
//                Log.d("ToolBar_item: ", "msg버튼클릭")
//                val intent = Intent(applicationContext, MessageRoomActivity::class.java)
//                startActivity(intent)
//                return true
//            }
            R.id.map -> {
                Log.d("ToolBar_item: ", "msg버튼클릭")
                supportFragmentManager.beginTransaction().replace(container, MapFragment())
                    .commitAllowingStateLoss()
//                val intent = Intent(applicationContext,AddItemActivity::class.java)
//                startActivity(intent)
                return true
            }

            R.id.toolbar_upload -> {
                Log.d("ToolBar_item: ", "검색버튼클릭")
                val intent = Intent(applicationContext, AddItemActivity::class.java)
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
            R.id.menuMyPage ->{
                Log.d("ToolBar_item: ", "msg버튼클릭")
                val intent = Intent(applicationContext, UserPageActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.toolbar_msg -> {
                Log.d("ToolBar_item: ", "msg버튼클릭")
                val intent = Intent(applicationContext, MessageRoomActivity::class.java)
                startActivity(intent)
                return true
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
            R.id.logindraw1 -> {
                Toast.makeText(this, "menu_item1 실행", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.menu1 -> {
                Toast.makeText(this, "menu_item2 실행", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, UserPageActivity::class.java)
                startActivity(intent)
            }
            R.id.menuMyPage1 -> Toast.makeText(this, "menu_item3 실행", Toast.LENGTH_SHORT).show()
        }
        return false
    }

}