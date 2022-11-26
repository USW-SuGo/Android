package com.example.sugo_mvc.retofit

import android.util.Log
import com.example.sugo_mvc.util.App
import com.example.sugo_mvc.util.Constants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


object RetrofitBuilder {
    var service: SuRetrofit

    val okHttpClient = OkHttpClient.Builder()
        //.addInterceptor(AuthInterceptor())
        .addInterceptor(getLogOkHttpClient())
        .build()

    init {

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
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        service = retrofit.create(SuRetrofit::class.java)
    }



    private fun getLogOkHttpClient(): Interceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d("Retrofit2", JSONObject(message).toString(4))
                message.isJsonArray() ->
                    Log.d("Retrofit2", JSONArray(message).toString(4))
                else ->
                    Log.d("Retrofit2", "CONNECTION INFO -> $message")
            }
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var req =

                chain.request().newBuilder().addHeader("Authorization", App.prefs.AccessToken ?: "")
                    .build()
            chain.request().newBuilder().addHeader("Authorization", App.prefs.RefreshToken ?: "")
                .build()

            return chain.proceed(req)
        }
    }

}

fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
