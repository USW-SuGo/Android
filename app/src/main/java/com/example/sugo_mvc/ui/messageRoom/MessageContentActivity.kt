package com.example.sugo_mvc.ui.messageRoom

import android.annotation.SuppressLint
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
        RetrofitBuilder.service.checkMessageContentRoom(accessToken,id!!.toLong(),0,10).enqueue(object :
            retrofit2.Callback<MutableList<NoteItem>> {
            override fun onResponse(call: retrofit2.Call<MutableList<NoteItem>>, response: Response<MutableList<NoteItem>>) {
                Log.d("msg",response.body().toString())
//                for(i in 0 ..response.body()!!.size-1) {
//                    if (senderid == response.body()!![i].messageSenderId)
//                        binding.messageContentRv.co
//                }
                binding.messageContentRv.layoutManager = LinearLayoutManager(this@MessageContentActivity)
                binding.messageContentRv.adapter = MessageContentAdapter(response.body()!!)
            }

            override fun onFailure(call: retrofit2.Call<MutableList<NoteItem>>, t: Throwable) {
                Log.d("good",t.toString())
            }

        })

         Log.d("edittext",binding.msg.text.toString())
        binding.msggo.setOnClickListener {
            val senderid = senderid
            val receiverid=receiverid
            Log.d("idre",senderid.toString()+receiverid.toString())
            var a=msgformat(id!!.toLong(),binding.msg.text.toString(),senderid,receiverid)

            RetrofitBuilder.service.chatput(accessToken,a).enqueue(object :
                retrofit2.Callback<SuccessCheckDto> {
                override fun onResponse(
                    call: retrofit2.Call<SuccessCheckDto>,
                    response: Response<SuccessCheckDto>
                ) {
                    Log.d("msg",binding.msg.text.toString())
                    Log.d("msg",response.body().toString())
                }

                override fun onFailure(call: retrofit2.Call<SuccessCheckDto>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}