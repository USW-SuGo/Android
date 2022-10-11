package com.example.sugo_mvc.retofit

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

    init{
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(AppInterceptor()))
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

     service = retrofit.create(SuRetrofit::class.java)
}
    private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient
            = OkHttpClient.Builder().run {
        addInterceptor(interceptor)
        build()
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader(AUTH_HEADER, "(header Value)")
                .build()
            proceed(newRequest)
        }
    }
}