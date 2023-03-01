package com.sugo.app.feat.network

import android.util.Log
import com.google.gson.*
import com.sugo.app.feat.App
import com.sugo.app.feat.App.Companion.prefs
import com.sugo.app.feat.ServiceLocator
import com.sugo.app.feat.model.*
import com.sugo.app.feat.model.request.*
import com.sugo.app.feat.model.response.JoinCheck
import com.sugo.app.feat.model.response.MyPage
import com.sugo.app.feat.model.response.NoteId
import com.sugo.app.feat.ui.common.TokenHeadersText
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Headers
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


interface SugoRetrofit {

    /**
     * 텍스트 정리
     * ui펼 api 정리하기
     * **/
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

    @POST("/token")
    fun refreshAccessToken(
        @Header("Authorization") refresh: String
    ): Call<Token>

    @POST("/user/find-id")
    suspend fun findlogin(
        @Body email: email
    ): Response<Success>

    @GET("/post/search")
    suspend fun searchProduct(
//        position:Int,
        @Query("value") value: String,
        @Query("category") category: String
    ): List<DealProduct>

    @GET("/post/{productPostId}")
    suspend fun detailProduct(
        @Path("productPostId") id: Long
    ): Response<DealProduct>

    @GET("/post/my-post")
    suspend fun getMypageProduct(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<DealProduct>

    @GET("/like-post")
    suspend fun getLikeProduct(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<DealProduct>

    @GET("/user")
    suspend fun getMyPage(): Response<MyPage>

    @POST("/post/up-post")
    suspend fun upPost(
        @Body productPostId: ProductPostId
    ): Response<Success>


    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    suspend fun deletePost(
        @Body productPostId: ProductPostId
    ): Response<Success>

    @POST("/post/close")
    suspend fun postClose(
        @Body productPostId: ProductPostId
    ): Response<Success>

    @POST("/user/check-loginId")
    suspend fun checkLoginId(
        @Body loginId: LoginId
    ): Response<Success>

    @POST("/user/check-email")
    suspend fun checkEmail(
        @Body email: email
    ): Response<Success>

    @POST("/user/join")
    suspend fun join(
        @Body userSign: UserSign
    ): Response<JoinCheck>

    @POST("/user/auth")
    suspend fun checkPayLoad(
        @Body PayLoad: PayLoad
    ): Response<Success>

    @POST("/note")
    suspend fun makeNote(
        @Body noteBody: NoteBody
    ): Response<NoteId>

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

            val interceptor = HttpLoggingInterceptor { message ->
                var accessToken = prefs.getAccessToken()
                when {
                    message.isJsonObject() ->
                        Log.d("Retrofit2", JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d("Retrofit2", JSONArray(message).toString(4))
                    else -> {
                        Log.d("Retrofit2", "CONNECTION INFO -> $message")
                    }
                }
            }
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        private const val baseUrl = "https://api.sugo-diger.com"
        fun create(): SugoRetrofit {
            val client = OkHttpClient.Builder()
                .addInterceptor(getLogOkHttpClient())
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(SugoRetrofit::class.java)
        }

        fun tokenCreate(): SugoRetrofit {
            val requestInterceptor = tokenInterceptor()
            val client = OkHttpClient.Builder()
                .authenticator(TokenAuthenticator())
                .addInterceptor(getLogOkHttpClient())
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(SugoRetrofit::class.java)
        }

        private fun tokenInterceptor(): Interceptor {
            var accessToken = prefs.getAccessToken()
            if (accessToken.isNullOrEmpty()) {
                accessToken = "null"
            }
            Log.d("token", accessToken)
            val requestInterceptor = Interceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder()
                if (accessToken != null) {
                    builder.addHeader("Authorization", "$accessToken")
                }
                chain.proceed(builder.build())
            }
            return requestInterceptor
        }


        fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
        fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
    }
}
