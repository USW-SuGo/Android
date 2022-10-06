package com.example.sugo_mvc.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sugo_mvc.data.*
import com.example.sugo_mvc.databinding.ActivityJoinBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.retofit.SuRetrofit
import com.example.sugo_mvc.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class JoinActivity : AppCompatActivity() {
    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.joinbtb.setOnClickListener {

//            val d = Log.d("test11", data.toString())
//            service.getLectureMainList( selectedType).enqueue(object : Callback<dataDto<MutableList<LectureMain?>>>{
//
//                override fun onResponse(
//                    call: Call<dataDto<MutableList<LectureMain?>>>,
//                    response: Response<dataDto<MutableList<LectureMain?>>>
//                ) {
//                    if(response.isSuccessful){
//                        var result=response.body()
//                        Log.d("성공", " 성공: " + result.toString());
//                    }else{
//                        Log.d("성공", "onFailure 에러: ")
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<dataDto<MutableList<LectureMain?>>>,
//                    t: Throwable
//                ) {
//                    Log.d("YMC", "onFailure 에러: " + t.message.toString());
//                }
//
//            })
            RetrofitBuilder.service.overlapemail(info = EmailOverLap("asadsasdd@naver.com")).enqueue(object :Callback<OverLapEmail>{
                override fun onResponse(
                    call: Call<OverLapEmail>,
                    response: Response<OverLapEmail>
                ) {
                    val result = response.body()
                    if (response.isSuccessful){
                        Log.d("overlap","$result")
                    }else{
                        Log.d("fail","asdsad")
                    }
                }

                override fun onFailure(call: Call<OverLapEmail>, t: Throwable) {
                    Log.d("onFailure",t.localizedMessage)
                }
            })

    }
    }

}
