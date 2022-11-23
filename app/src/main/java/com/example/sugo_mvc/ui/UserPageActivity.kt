package com.example.sugo_mvc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugo_mvc.R
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.Userpage
import com.example.sugo_mvc.databinding.ActivityDealDetailBinding
import com.example.sugo_mvc.databinding.ActivityUserPageBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.ui.deal.DealAdapter
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPageActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserPageBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val accessToken = App.prefs.AccessToken!!.replace("AccessToken=","")
        RetrofitBuilder.service.getUserPage(accessToken).enqueue(object : Callback<Userpage>{
            override fun onResponse(call: Call<Userpage>, response: Response<Userpage>) {
                Log.d("user",call.request().toString())
                binding.userrv.layoutManager = LinearLayoutManager(this@UserPageActivity)
                binding.userrv.adapter = userPageAdapter(response.body()!!.myPosting as MutableList<DealMainItem>)
                binding.usernickname.text= response.body()!!.nickname.toString()
                binding.countTradeAttempt.text=response.body()!!.countTradeAttempt.toString()
            }

            override fun onFailure(call: Call<Userpage>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}