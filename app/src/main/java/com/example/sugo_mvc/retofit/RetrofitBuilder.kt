package com.example.sugo_mvc.retofit

import com.example.sugo_mvc.ui.JoinActivity
import com.example.sugo_mvc.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
    var service :SuRetrofit
    var clientBuilder = OkHttpClient.Builder()
    var loggingInterceptor = HttpLoggingInterceptor()

    init{
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(clientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
     service = retrofit.create(SuRetrofit::class.java)
}
}