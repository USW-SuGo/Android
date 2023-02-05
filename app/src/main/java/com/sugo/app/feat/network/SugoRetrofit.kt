package com.sugo.app.feat.network

import android.util.Log
import com.google.gson.*
import com.sugo.app.feat.App.Companion.prefs
import com.sugo.app.feat.model.*
import com.sugo.app.feat.repository.repo.Token.TokenPreferenceManager
import com.sugo.app.feat.ui.login.LoginViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


interface SugoRetrofit {

    @GET("/post/all")
    suspend fun getMainPage(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("category") category: String
    ): List<DealProduct>

    @Headers("Content-Type:application/json")
    @POST("/user/login")
    suspend fun login(
        @Body loginFormat: LoginFormat
    ): Response<Unit>

    @POST("/user/find-id")
    suspend fun findlogin(
        @Body email: email
    ): Response<Success>



    companion object {

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

        private fun getLogOkHttpClient(): Interceptor {
            var accessToken=prefs.getAccessToken()
            val interceptor = HttpLoggingInterceptor { message ->
                when {
                    message.isJsonObject() ->
                        Log.d("Retrofit2", JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d("Retrofit2", JSONArray(message).toString(4))
                    else ->{
                        Log.d("Retrofit2", "CONNECTION INFO -> $message")
                    Log.d("Retrofit", "Request Headers:  $accessToken")
                }}

            }
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        private const val baseUrl = "https://api.sugo-diger.com"
        fun create(): SugoRetrofit {



//            if(accessToken.isNullOrEmpty()){
//                accessToken="null"
//            }
//            val requestInterceptor = Interceptor { chain ->
//                val original = chain.request()
//                val builder = original.newBuilder()
//                Log.d("Retrofit", "Request Headers:  $accessToken")
//                if (accessToken != null) {
//                    builder.header("Authorization", "Bearer $accessToken")
//                }
//                chain.proceed(builder.build())
//            }
            val client = OkHttpClient.Builder()
                .addInterceptor(getLogOkHttpClient())
//                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(NullOnEmptyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(SugoRetrofit::class.java)
        }


        fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
        fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
    }
}
//}
//
//}