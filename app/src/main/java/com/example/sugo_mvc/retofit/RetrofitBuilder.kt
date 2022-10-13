package com.example.sugo_mvc.retofit

import com.example.sugo_mvc.util.App
import com.example.sugo_mvc.util.Constants.Companion.AUTH_HEADER
import com.example.sugo_mvc.util.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type


object RetrofitBuilder {
    var service :SuRetrofit
    var clientBuilder = OkHttpClient.Builder()
    var loggingInterceptor = HttpLoggingInterceptor()
    val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()
    init{
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

     service = retrofit.create(SuRetrofit::class.java)
}

    class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var req =
                chain.request().newBuilder().addHeader("Authorization", App.prefs.AccessToken ?: "").build()
                chain.request().newBuilder().addHeader("Authorization", App.prefs.RefreshToken ?: "").build()
            return chain.proceed(req)
        }
    }

}
