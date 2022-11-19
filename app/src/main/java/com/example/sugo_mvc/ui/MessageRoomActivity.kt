package com.example.sugo_mvc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.roomAll
import com.example.sugo_mvc.data.roomInfo
import com.example.sugo_mvc.databinding.ActivityJoinBinding
import com.example.sugo_mvc.databinding.ActivityMessageRoomBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.ui.deal.DealAdapter
import com.example.sugo_mvc.ui.messageRoom.MessageAdapter
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
        RetrofitBuilder.service.checkMessageRoom(accessToken).enqueue(object : Callback<MutableList<roomInfo>> {
            override fun onResponse(call: Call<MutableList<roomInfo>>, response: Response<MutableList<roomInfo>>) {

                Log.d("asd",response.body().toString())
                binding.messageRoomRv.layoutManager = LinearLayoutManager(this@MessageRoomActivity)
                binding.messageRoomRv.adapter = MessageAdapter(response.body()!!)
            }

            override fun onFailure(call: Call<MutableList<roomInfo>>, t: Throwable) {
                Log.d("asd",t.toString())
            }
        })
    }
}