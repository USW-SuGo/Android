package com.example.sugo_mvc.ui.messageRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sugo_mvc.data.SuccessCheckDto
import com.example.sugo_mvc.data.msgformat
import com.example.sugo_mvc.databinding.ActivityMessageContentBinding
import com.example.sugo_mvc.databinding.ActivitySendMsgBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import retrofit2.Response

class SendMsgActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySendMsgBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id: String? = intent.getStringExtra("id")
        var senderid:Long = intent.getStringExtra("senderid")!!.toLong()
        var receiverid:Long = intent.getStringExtra("receiverid")!!.toLong()
        val accessToken = App.prefs.AccessToken!!.replace("AccessToken=","")
        binding.msggo.setOnClickListener {
            val senderid = senderid
            val receiverid=receiverid
            Log.d("idre",senderid.toString()+receiverid.toString())
            var a= msgformat(id!!.toLong(),binding.msg.text.toString(),senderid,receiverid)

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