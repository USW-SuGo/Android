package com.example.sugo_mvc.retofit

import com.example.sugo_mvc.data.*
import com.example.sugo_mvc.util.Constants.Companion.AUTH_HEADER
import com.example.sugo_mvc.util.Constants.Companion.EXIST
import com.example.sugo_mvc.util.Constants.Companion.EmailSend
import com.example.sugo_mvc.util.Constants.Companion.LOGINCHECK
import com.example.sugo_mvc.util.Constants.Companion.OVERLAP
import com.example.sugo_mvc.util.Constants.Companion.REQUEST_REFRESH
import com.example.sugo_mvc.util.Constants.Companion.SIGN_UP
import retrofit2.Call
import retrofit2.http.*


interface SuRetrofit {
    //이메일 중복 확인
    @POST(EXIST)
    fun  existEmail(@Body info : Email):Call<CheckExist>
    //아이디 중복확인
    @POST(LOGINCHECK)
    fun checkLoginId(@Body info: Login):Call<CheckExist>
    //이메일 인증시 요청 보내기
    @POST(EmailSend)
    fun emailSend(@Body info : Email) :Call<SuccessCheckDto>
    //회원가입
    @POST(SIGN_UP)
    fun signUp(@Body info : UserInfo):Call<SuccessCheckDto>

    @POST(REQUEST_REFRESH)
    fun requestRefresh(@Header(AUTH_HEADER) refresh: String): Call<Token>

    @POST(OVERLAP)
    fun overlapemail(@Body info : EmailOverLap):Call<OverLapEmail>


}
//@GET(LECTURE_MAIN)
//fun getLectureMainList(
//    @Query("option") option: String,
//    @Query("page") page: Int = 1,
//    @Query("majorType") majorType: String = ""
//): Call<dataDto<MutableList<LectureMain?>>>