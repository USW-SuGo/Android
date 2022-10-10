package com.example.sugo_mvc.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sugo_mvc.data.*
import com.example.sugo_mvc.databinding.ActivityJoinBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JoinActivity : AppCompatActivity() {
    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.joinbtb.setOnClickListener {
        val email=binding.loginEmail.editText?.text.toString()
        val loginid=binding.loginId.editText?.text.toString()
        val loginpwd=binding.loginPwd.editText?.text.toString()
        Log.d("Test",email)
        Log.d("Test",loginid)
        Log.d("Test",loginpwd)


//            val d = Log.d("test11", data.toString())
//            service.getLectureMainList( selectedType).enqueue(object : Callback<dataDto<MutableList<LectureMain?>>>{
//
//                override fun onResponse(
//                    call: Call<dataDto<MutableList<LectureMain?>>>,
//                    response: Response<dataDto<MutableList<LectureMain?>>>
//                ) {
//                    if(response.isSuccessful){
//                        var result=response.body()
//                        Log.d("성공", " 성공: " + result.toString());
//                    }else{
//                        Log.d("성공", "onFailure 에러: ")
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<dataDto<MutableList<LectureMain?>>>,
//                    t: Throwable
//                ) {
//                    Log.d("YMC", "onFailure 에러: " + t.message.toString());
//                }
//
//            })
            RetrofitBuilder.service.existEmail(Email(email)).enqueue(object :Callback<CheckExist>{
                override fun onResponse(
                    call: Call<CheckExist>,
                    response: Response<CheckExist>) {
                    val result=response.body()
                    if(!validateEmail()){
                        Log.d("다시입력","다시입려고해주세요")
                    }
                    if (response.isSuccessful){
                        Log.d("exist","$result")
                    }else{
                        Log.d("fail1","fali")
                    }

                }

                override fun onFailure(call: Call<CheckExist>, t: Throwable) {
                    Log.d("onFailure",t.localizedMessage)
                }

            })
            RetrofitBuilder.service.checkLoginId(Login(loginid)).enqueue(object :Callback<CheckExist>{
                override fun onResponse(call: Call<CheckExist>, response: Response<CheckExist>) {
                    val result1=response.body()
                    if(!validateLoginId()){
                        Log.d("다시입력","다시입려고해주세요")
                    }
                    if (response.isSuccessful){
                        Log.d("exist","$result1")
                    }else{
                        Log.d("fail1","fali")
                    }
                }

                override fun onFailure(call: Call<CheckExist>, t: Throwable) {
                    Log.d("onFailure",t.localizedMessage)
                }

            })
//

    }
    }
    private fun validateEmail(): Boolean {
        val value: String = binding.loginEmail.editText?.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return if (value.isEmpty()) {
            binding.loginEmail.error = "이메일을 입력해주세요."
            false
        } else if (!value.matches(emailPattern.toRegex())) {
            binding.loginEmail.error = "이메일 형식이 잘 못 되었습니다."
            false
        } else {
            binding.loginEmail.error = null
            binding.loginEmail.isErrorEnabled = false
            true
        }
    }
    private fun validateLoginId(): Boolean {
        val value: String = binding.loginId.editText?.text.toString()
        val loginPattern = "[a-z]+[0-9]"

        return if (value.isEmpty()) {
            binding.loginEmail.error = "아이디을 입력해주세요."
            false
        } else if (!value.matches(loginPattern.toRegex())) {
            binding.loginEmail.error = "아이디 형식이 잘 못 되었습니다."
            false
        } else {
            binding.loginEmail.error = null
            binding.loginEmail.isErrorEnabled = false
            true
        }
    }
}
