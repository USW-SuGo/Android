package com.sugo.app.feat.network

import android.util.Log
import com.google.gson.*
import com.sugo.app.feat.App.Companion.prefs
import com.sugo.app.feat.common.NetWork.AuthPayLoad
import com.sugo.app.feat.common.NetWork.BASE_URL
import com.sugo.app.feat.common.NetWork.CHATROOM
import com.sugo.app.feat.common.NetWork.CheckEmail
import com.sugo.app.feat.common.NetWork.CheckId
import com.sugo.app.feat.common.NetWork.ClosePost
import com.sugo.app.feat.common.NetWork.DetailProduct
import com.sugo.app.feat.common.NetWork.FCMTOKEN
import com.sugo.app.feat.common.NetWork.FindId
import com.sugo.app.feat.common.NetWork.GetUserCloseDealItem
import com.sugo.app.feat.common.NetWork.GetUserDealItem
import com.sugo.app.feat.common.NetWork.Join
import com.sugo.app.feat.common.NetWork.LIKE
import com.sugo.app.feat.common.NetWork.LikePost
import com.sugo.app.feat.common.NetWork.Login
import com.sugo.app.feat.common.NetWork.MainPage
import com.sugo.app.feat.common.NetWork.MyPage
import com.sugo.app.feat.common.NetWork.MyPageProduct
import com.sugo.app.feat.common.NetWork.NOTEROOM
import com.sugo.app.feat.common.NetWork.Note
import com.sugo.app.feat.common.NetWork.POSTUPLOAD
import com.sugo.app.feat.common.NetWork.SENDCHAT
import com.sugo.app.feat.common.NetWork.SEND_FILE
import com.sugo.app.feat.common.NetWork.Search
import com.sugo.app.feat.common.NetWork.TokenRefreshAPI
import com.sugo.app.feat.common.NetWork.UpPost
import com.sugo.app.feat.common.NetWork.getClosePost
import com.sugo.app.feat.model.*
import com.sugo.app.feat.model.request.*
import com.sugo.app.feat.model.response.*
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
    @GET(MainPage)
    suspend fun getMainPage(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("category") category: String
    ): List<DealProduct>

    @GET(getClosePost)
    suspend fun getClosePost(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<DealProduct>

    @Headers("Content-Type:application/json")
    @POST(Login)
    suspend fun login(
        @Body loginFormat: LoginFormat
    ): Response<Unit>

    @POST(TokenRefreshAPI)
    fun refreshAccessToken(
        @Header("Authorization") refresh: String
    ): Call<Token>

    @POST(FindId)
    suspend fun findlogin(
        @Body email: email
    ): Response<Success>

    @Multipart
    @POST(POSTUPLOAD)
    fun postUpload(
        @Part title: MultipartBody.Part,
        @Part content: MultipartBody.Part,
        @Part price: MultipartBody.Part,
        @Part contactPlace: MultipartBody.Part,
        @Part category: MultipartBody.Part,
        @Part image: MutableList<MultipartBody.Part>
    ): Call<ProductPostId>

    @GET(Search)
    suspend fun searchProduct(

        @Query("value") value: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<DealProduct>

    @GET(DetailProduct)
    suspend fun detailProduct(
        @Path("productPostId") id: Long
    ): Response<DealProduct>

    @GET(MyPageProduct)
    suspend fun getMypageProduct(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<DealProduct>
    @GET(GetUserDealItem)
    suspend fun getUserPageProduct(
        @Path("userId") id: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<DealProduct>

    @GET(GetUserCloseDealItem)
    suspend fun getUserCompletePageProduct(
        @Path("userId") id: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<DealProduct>

    @GET(MyPage)
    suspend fun getMyPage(): Response<MyPage>
    @GET("/user/{userId}")
    suspend fun getUser(
        @Path("userId") userId: Long
    ):Response<User>

    @POST(UpPost)
    suspend fun upPost(
        @Body productPostId: ProductPostId
    ): Response<Success>

    @POST("/user/manner")
    suspend fun setManner(
        @Body mannerTarget:MannerTarget
    ): Response<Success>


    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    suspend fun deletePost(
        @Body productPostId: ProductPostId
    ): Response<Success>

    @POST(ClosePost)
    suspend fun postClose(
        @Body productPostId: ProductPostId
    ): Response<Success>

    @POST(CheckId)
    suspend fun checkLoginId(
        @Body loginId: LoginId
    ): Response<Success>

    @POST(CheckEmail)
    suspend fun checkEmail(
        @Body email: email
    ): Response<Success>

    @POST(Join)
    suspend fun join(
        @Body userSign: UserSign
    ): Response<JoinCheck>

    @POST(AuthPayLoad)
    suspend fun checkPayLoad(
        @Body PayLoad: PayLoad
    ): Response<Success>

    @POST(Note)
    suspend fun makeNote(
        @Body noteBody: NoteBody
    ): Response<NoteId>

    @POST(LIKE)
    suspend fun like(
        @Body productPostId: ProductPostId
    ): Response<Like>

    @GET(NOTEROOM)
    suspend fun getNoteRoom(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<Any>>

    @GET(LikePost)
    suspend fun getLikeProduct(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<DealProduct>

    @GET(CHATROOM)
    suspend fun getNoteContent(
        @Path("noteId") noteId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<Any>>

    @POST(SENDCHAT)
    suspend fun sendChat(
        @Body chat: Chat
    ): Response<Any>

    @Multipart
    @POST(SEND_FILE)
    suspend fun sendFile(
//        @Part("chatFile") chatFile: RequestBody,
        @Part noteId: MultipartBody.Part,
        @Part senderId: MultipartBody.Part,
        @Part receiverId: MultipartBody.Part,
        @Part multipartFileList: MutableList<MultipartBody.Part>
    ): Response<Any>

    @PATCH(FCMTOKEN)
    suspend fun sendFCM(
        @Body fcmToken: FcmToken
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

            val interceptor = HttpLoggingInterceptor { message ->
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

        private const val baseUrl = BASE_URL
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

            val requestInterceptor = Interceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder()
                if (prefs.getAccessToken() != null) {
                    builder.addHeader("Authorization", "${prefs.getAccessToken()}")
                }
                chain.proceed(builder.build())
            }
            return requestInterceptor
        }


        fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
        fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
    }
}
