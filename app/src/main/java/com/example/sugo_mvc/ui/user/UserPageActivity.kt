package com.example.sugo_mvc.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.Userpage
import com.example.sugo_mvc.databinding.ActivityUserPageBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.ui.login.LoginActivity
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPageActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserPageBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val safebtn: String? = intent.getStringExtra("safebtn")
        if (safebtn=="1"){
            dialog(this).showDialog()
        }
        val accessToken = App.prefs.AccessToken!!.replace("AccessToken=","")

        RetrofitBuilder.service.getUserPage().enqueue(object : Callback<Userpage>{
            override fun onResponse(call: Call<Userpage>, response: Response<Userpage>) {
                if(response.isSuccessful){
                Log.d("user",call.request().toString())
                binding.userrv.layoutManager = LinearLayoutManager(this@UserPageActivity)
                binding.userrv.adapter = userPageAdapter(response.body()!!.myPosting as MutableList<DealMainItem>)
                binding.userPageNickName.text="오늘도 수고하세요! "+response.body()!!.nickname.toString()
                binding.countTradeAttempt.text=response.body()!!.countTradeAttempt.toString()
            }else{
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
            }

            }

            override fun onFailure(call: Call<Userpage>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
}