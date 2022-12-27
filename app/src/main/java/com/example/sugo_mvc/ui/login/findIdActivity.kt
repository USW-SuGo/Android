package com.example.sugo_mvc.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sugo_mvc.R
import com.example.sugo_mvc.data.Email
import com.example.sugo_mvc.data.SuccessCheckDto
import com.example.sugo_mvc.databinding.ActivityFindIdBinding
import com.example.sugo_mvc.databinding.ActivityLoginBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Response

class findIdActivity : AppCompatActivity() {
    private val binding by lazy {ActivityFindIdBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.findidbtn.setOnClickListener {


            RetrofitBuilder.service.findId(Email(binding.email.text.toString()))
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