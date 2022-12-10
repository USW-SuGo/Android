package com.example.sugo_mvc.retofit

//import com.example.sugo_mvc.util.Constants.Companion.NOTE
import com.example.sugo_mvc.data.*
import com.example.sugo_mvc.util.Constants.Companion.AUTH_HEADER
import com.example.sugo_mvc.util.Constants.Companion.CHECKMESSAGECONTENTROOM
import com.example.sugo_mvc.util.Constants.Companion.CHECKMESSAGEROOM
import com.example.sugo_mvc.util.Constants.Companion.DETAILPAGE
import com.example.sugo_mvc.util.Constants.Companion.EXIST
import com.example.sugo_mvc.util.Constants.Companion.FindId
import com.example.sugo_mvc.util.Constants.Companion.FindPwd
import com.example.sugo_mvc.util.Constants.Companion.LOGIN
import com.example.sugo_mvc.util.Constants.Companion.LOGINCHECK
import com.example.sugo_mvc.util.Constants.Companion.MAKEMESSAGEROOM
import com.example.sugo_mvc.util.Constants.Companion.MYPAGE
import com.example.sugo_mvc.util.Constants.Companion.OVERLAP
import com.example.sugo_mvc.util.Constants.Companion.POSTGET
import com.example.sugo_mvc.util.Constants.Companion.POSTUPLOAD
import com.example.sugo_mvc.util.Constants.Companion.REQUEST_REFRESH
import com.example.sugo_mvc.util.Constants.Companion.SIGN_UP
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File


interface SuRetrofit {
    //이메일 중복 확인
    @POST(EXIST)
    fun existEmail(@Body info: Email): Call<CheckExist>

    //아이디 중복확인
    @POST(LOGINCHECK)
    fun checkLoginId(@Body info: Login): Call<CheckExist>

    //이메일 인증시 요청 보내기
    //회원가입
    @POST(SIGN_UP)
    fun signUp(@Body info: UserInfo): Call<SuccessCheckDto>

    //로그인
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @POST(LOGIN)
    fun login(@Body info: LoginFormat): Call<Token>

    //아이디 찾기
    @POST(FindId)
    fun findId(@Body info: Email): Call<SuccessCheckDto>

    @POST(FindPwd)
    fun findPwd(@Body info: LoginFormat): Call<SuccessCheckDto>

    @POST(REQUEST_REFRESH)
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): Call<Token>

    @POST(OVERLAP)
    fun overlapemail(@Body info: EmailOverLap): Call<OverLapEmail>

    @Multipart
    @POST(POSTUPLOAD)
    fun postUpload(
        @Header(AUTH_HEADER) AccessToken: String,
//        @Part ("PostFormat") PostFormat: PostFormat,
        @Part title: MultipartBody.Part,
        @Part content: MultipartBody.Part,
        @Part price: MultipartBody.Part,
        @Part contactPlace: MultipartBody.Part,
        @Part category: MultipartBody.Part,
        @Part image: MutableList<MultipartBody.Part>


    ): Call<ProductPostId>

    @GET(POSTGET)
    fun getItemList(
        @Query("page") page: Int ,
        @Query("size") size: Int ,
    ): Call<MutableList<DealMainItem>>

    @GET(DETAILPAGE)
    fun getDetailPage(
        @Header("Authorization") AccessToken: String,
        @Query("productPostId") Id: Long
    ): Call<DealDetailItem>

    @GET(MYPAGE)
    fun getUserPage(
        @Header("Authorization") AccessToken: String,
    ): Call<Userpage>

    @POST(MAKEMESSAGEROOM)
    fun makeMessageRoom(
        @Header("Authorization") AccessToken: String,
        @Body info: noteRoom
    )

    @GET(CHECKMESSAGEROOM)
    fun checkMessageRoom(
        @Header("Authorization") AccessToken: String
    ): Call<MutableList<roomInfo>>

    @GET(CHECKMESSAGECONTENTROOM)
    fun checkMessageContentRoom(
        @Header("Authorization") AccessToken: String,
        @Query("noteId") noteId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<MutableList<NoteItem>>

    @POST("/note-content/")
    fun chatput(
        @Header("Authorization") AccessToken: String,
        @Body info: msgformat
    ): Call<SuccessCheckDto>

    @POST("/post/up-post")
    fun upPost(
        @Header("Authorization") AccessToken: String,
        @Body info:ProductPostId
    ): Call<SuccessCheckDto>

    @POST("/like")
    fun like(
        @Header("Authorization") AccessToken: String,
        @Body info:ProductPostId
    ):Call<Like>
    @POST("/token")
    fun refresh(
        @Header("Authorization") RefreshToken: String,

    ):Call<Token>

    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    fun deletePost(
        @Header("Authorization") AccessToken: String,
        @Body info:ProductPostId
    ): Call<SuccessCheckDto>
}


