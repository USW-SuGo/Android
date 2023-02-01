package com.sugo.app.feat.network

import android.util.Log
import com.google.gson.*
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.LoginFormat
import com.sugo.app.feat.model.Token
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface SugoRetrofit {

    @GET("/post/all")
    suspend fun getMainPage(
        @Query("page") page:Int,
        @Query("size") size:Int,
        @Query("category") category:String
    ):List<DealProduct>

    @POST("/user/login")
    suspend fun login(
        @Body info : LoginFormat
    ):Response<Token>

    companion object{

        val gson = gsonBuilder()

        private fun gsonBuilder(): Gson? {
            val gson = GsonBuilder()
                .registerTypeAdapter(
                    LocalDateTime::class.java,
                    object : JsonDeserializer<LocalDateTime> {
                        override fun deserialize(
                            json: JsonElement?,
                            typeOfT: Type?,
                            context: JsonDeserializationContext?
                        ): LocalDateTime {
                            return LocalDateTime.parse(
                                json?.asString,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                            )
                        }
                    }
                )
                .create()
            return gson
        }

        private const val baseUrl ="https://api.sugo-diger.com"
        fun create(): SugoRetrofit {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(SugoRetrofit::class.java)
        }

    }
}