package com.example.sugo_mvc.retofit

import android.content.Intent
import android.net.Uri
import com.example.sugo_mvc.data.*
import com.example.sugo_mvc.util.Constants.Companion.AUTH_HEADER
import com.example.sugo_mvc.util.Constants.Companion.EXIST

import com.example.sugo_mvc.util.Constants.Companion.FindId
import com.example.sugo_mvc.util.Constants.Companion.FindPwd
import com.example.sugo_mvc.util.Constants.Companion.LECTURE_MAIN
import com.example.sugo_mvc.util.Constants.Companion.LOGIN
import com.example.sugo_mvc.util.Constants.Companion.LOGINCHECK
import com.example.sugo_mvc.util.Constants.Companion.OVERLAP
import com.example.sugo_mvc.util.Constants.Companion.POSTGET
import com.example.sugo_mvc.util.Constants.Companion.POSTUPLOAD
import com.example.sugo_mvc.util.Constants.Companion.REQUEST_REFRESH
import com.example.sugo_mvc.util.Constants.Companion.SIGN_UP
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface SuRetrofit {
    //이메일 중복 확인
    @POST(EXIST)
    fun  existEmail(@Body info : Email):Call<CheckExist>
    //아이디 중복확인
    @POST(LOGINCHECK)
    fun checkLoginId(@Body info: Login):Call<CheckExist>
    //이메일 인증시 요청 보내기
    //회원가입
    @POST(SIGN_UP)
    fun signUp(@Body info : UserInfo):Call<SuccessCheckDto>
    //로그인
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST(LOGIN)
    fun login(@Body info: LoginFormat): Call<Token>
    //아이디 찾기
    @POST(FindId)
    fun findId(@Body info : Email):Call<SuccessCheckDto>
    @POST(FindPwd)
    fun findPwd(@Body info : LoginFormat):Call<SuccessCheckDto>

    @POST(REQUEST_REFRESH)
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): Call<Token>

    @POST(OVERLAP)
    fun overlapemail(@Body info : EmailOverLap):Call<OverLapEmail>

    @Multipart
    @POST(POSTUPLOAD)
    fun postUpload(
        @Header(AUTH_HEADER) AccessToken:String,
        @Part image: MultipartBody.Part,
        @Part ("info") PostFormat: PostFormat
    ):Call<ProductPostId>

    @GET(POSTGET)
    fun getItemList(
        @Query("page") page: Int =1,
        @Query("size") size: Int =10,
    ):Call<MutableList<DealMainItem?>>
@GET(LECTURE_MAIN)
fun getLectureMainList(
    @Query("option") option: String,
    @Query("page") page: Int = 1,
    @Query("majorType") majorType: String = ""
): Call<dataDto<MutableList<LectureMain?>>>
}


