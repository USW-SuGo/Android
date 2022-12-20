package com.example.sugo_mvc.ui.messageRoom

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugo_mvc.R
import com.example.sugo_mvc.data.NoteItem
import com.example.sugo_mvc.data.SuccessCheckDto
import com.example.sugo_mvc.data.msgformat
import com.example.sugo_mvc.databinding.ActivityMessageContentBinding
import com.example.sugo_mvc.databinding.ActivityMessageRoomBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import retrofit2.Response
import javax.security.auth.callback.Callback

class MessageContentActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMessageContentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id: String? = intent.getStringExtra("id")
        val sender : String? = intent.getStringExtra("senderid")
        var senderid:Long = intent.getStringExtra("senderid")!!.toLong()
        var receiverid:Long = intent.getStringExtra("receiverid")!!.toLong()
        val accessToken = App.prefs.AccessToken!!.replace("AccessToken=","")
        RetrofitBuilder.service.checkMessageContentRoom(id!!.toLong(),0,100).enqueue(object :
            retrofit2.Callback<MutableList<NoteItem>> {
            override fun onResponse(call: retrofit2.Call<MutableList<NoteItem>>, response: Response<MutableList<NoteItem>>) {
                Log.d("msg",response.body().toString())
                binding.messageContentRv.layoutManager = LinearLayoutManager(this@MessageContentActivity)
                binding.messageContentRv.adapter = MessageContentAdapter(response.body()!!)
            }

            override fun onFailure(call: retrofit2.Call<MutableList<NoteItem>>, t: Throwable) {
                Log.d("good",t.toString())
            }
        })
        binding.pullContent.setOnRefreshListener {
            RetrofitBuilder.service.checkMessageContentRoom(id!!.toLong(),0,100).enqueue(object :
                retrofit2.Callback<MutableList<NoteItem>> {
                override fun onResponse(call: retrofit2.Call<MutableList<NoteItem>>, response: Response<MutableList<NoteItem>>) {
                    Log.d("msg",response.body().toString())
                    binding.messageContentRv.layoutManager = LinearLayoutManager(this@MessageContentActivity)
                    binding.messageContentRv.adapter = MessageContentAdapter(response.body()!!)
                }

                override fun onFailure(call: retrofit2.Call<MutableList<NoteItem>>, t: Throwable) {
                    Log.d("good",t.toString())
                }
            })
            binding.pullContent.isRefreshing=false
        }
       binding.sendMsg.setOnClickListener{
           val intent = Intent(applicationContext, SendMsgActivity::class.java)
           intent.putExtra("senderid",  senderid.toString())
           intent.putExtra("receiverid",   receiverid.toString())
           intent.putExtra("id",   id)
           startActivity(intent)
       }

    }
}