package com.example.sugo_mvc.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sugo_mvc.data.Email
import com.example.sugo_mvc.data.LoginFormat
import com.example.sugo_mvc.data.SuccessCheckDto
import com.example.sugo_mvc.data.findPwd
import com.example.sugo_mvc.databinding.ActivityFindIdBinding
import com.example.sugo_mvc.databinding.ActivityFindPwdBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Response

class FindPwdActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFindPwdBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.findidbtn.setOnClickListener {


            RetrofitBuilder.service.findPwd(findPwd( binding.id.text.toString(),binding.email.text.toString()))
                .enqueue(object : retrofit2.Callback<SuccessCheckDto> {
                    override fun onResponse(
                        call: Call<SuccessCheckDto>,
                        response: Response<SuccessCheckDto>
                    ) {
                        Log.d("success", response.body().toString())
                    }

                    override fun onFailure(call: Call<SuccessCheckDto>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}