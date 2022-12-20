package com.example.sugo_mvc.ui.messageRoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugo_mvc.data.roomInfo
import com.example.sugo_mvc.databinding.ActivityMessageRoomBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.ui.login.LoginActivity
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageRoomActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMessageRoomBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val accessToken = App.prefs.AccessToken!!.replace("AccessToken=","")
        RetrofitBuilder.service.checkMessageRoom().enqueue(object : Callback<MutableList<roomInfo>> {
            override fun onResponse(call: Call<MutableList<roomInfo>>, response: Response<MutableList<roomInfo>>) {
                if(response.isSuccessful) {
                    Log.d("asd", response.body().toString())
                    binding.messageRoomRv.layoutManager =
                        LinearLayoutManager(this@MessageRoomActivity)
                    binding.messageRoomRv.adapter = MessageAdapter(response.body()!!)
                }else{
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)

                }

            }

            override fun onFailure(call: Call<MutableList<roomInfo>>, t: Throwable) {
                Log.d("asd",t.toString())
            }
        })
    }
}