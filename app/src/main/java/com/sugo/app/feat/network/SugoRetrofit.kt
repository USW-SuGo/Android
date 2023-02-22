package com.sugo.app.feat.network

import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.*
import com.sugo.app.feat.App
import com.sugo.app.feat.App.Companion.prefs
import com.sugo.app.feat.ServiceLocator
import com.sugo.app.feat.model.*
import com.sugo.app.feat.ui.MainActivity
import com.sugo.app.feat.ui.common.TokenHeadersText
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.intellij.lang.annotations.Identifier
import org.intellij.lang.annotations.JdkConstants
import org.json.JSONArray
import org.json.JSONObject
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
        @Header("authorization") refresh: String
    ): Response<Unit>

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
    suspend fun getMyPage():Response<MyPage>

    @POST("/post/up-post")
    suspend fun upPost(
        @Body productPostId: ProductPostId
    ): Response<Success>


    @HTTP(method = "DELETE", path="/post", hasBody = true)
    suspend fun deletePost(
        @Body productPostId: ProductPostId
    ): Response<Success>

    @POST("/post/close")
    suspend fun postClose(
        @Body productPostId: ProductPostId
    ): Response<Success>

    @POST("/user/check-loginId")
    suspend fun checkLoginId(
        @Body   loginId: LoginId
    ): Response<Success>

    @POST("/user/check-email")
    suspend fun checkEmail(
        @Body   email: email
    ): Response<Success>

    @POST("/user/join")
    suspend fun join(
        @Body  userSign: UserSign
    ): Response<JoinCheck>

    @POST("/user/auth")
    suspend fun checkPayLoad(
        @Body  PayLoad:PayLoad
    ): Response<Success>
    class TokenAuthenticator : Authenticator {
        override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
            val refreshTokenRequest = App.prefs.getRefreshToken().toString()
            val response1 =
                ServiceLocator.provideTokenApiClient().refreshAccessToken(refreshTokenRequest)
            val originalHeaders = response1.headers().get("authorization")
            val (accessToken, refreshToken) = TokenHeadersText(originalHeaders)
            return if (handleResponse(accessToken, refreshToken, response1)) {
                Log.d("실패실패2","실패했어요 토큰")
                response.request
                    .newBuilder()
                    .header("authorization", accessToken)
                    .build()
            } else {
                null
            }
        }

        private fun handleResponse(
            accessToken: String,
            refreshToken: String,
            response: Response<Unit>
        ) =
            if (response.isSuccessful && response.headers().get("authorization") != null) {
                prefs.saveAccessToken(accessToken)
                Log.d("성공성공","실패했어요 토큰")
                prefs.saveRefreshToken(refreshToken)
                true
            } else {
                Log.d("실패실패","실패했어요 토큰")
                prefs.saveAccessToken("")
                prefs.saveRefreshToken("")
                false
            }

    }

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
                .addInterceptor(getLogOkHttpClient())
                .addInterceptor(requestInterceptor)
                .addInterceptor { chain ->
                    val request = chain.request()
                    val response = chain.proceed(request)
                    if (response.code == 401) {
                        TokenAuthenticator()
                        Log.d("TokenAuthenticator","test")
                    }
                    response
                }
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
            val requestInterceptor = Interceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder()
                Log.d("Retrofit", "Request Headers:  $accessToken")
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
