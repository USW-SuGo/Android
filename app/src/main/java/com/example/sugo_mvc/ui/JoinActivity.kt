package com.example.sugo_mvc.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sugo_mvc.data.*
import com.example.sugo_mvc.databinding.ActivityJoinBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JoinActivity : AppCompatActivity() {
    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.joinbtb.setOnClickListener {
            val loginid=binding.loginId.text.toString()
            val loginpwd=binding.loginPwd.text.toString()
            val email=binding.loginEmail.text.toString()

        Log.d("Test",email)
        Log.d("Test",loginid)
        Log.d("Test",loginpwd)

            if(!validateEmail()){
                Log.d("다시입력","다시입려고해주세요")
            }
            if(!validateLoginId()){
                Log.d("다시입력2","다시입려고해주세요")
            }
            if(!validateLoginPwd()){
                Log.d("다시입력3","다시입려고해주세요")
            }
            RetrofitBuilder.service.existEmail(Email(email)).enqueue(object :Callback<CheckExist>{
                override fun onResponse(
                    call: Call<CheckExist>,
                    response: Response<CheckExist>) {
                    val result=response.body()

                    if (response.isSuccessful){
                        Log.d("exist", response.body().toString())
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
        RetrofitBuilder.service.signUp(UserInfo(loginid,email,loginpwd,"정보보호학과")).enqueue(object :Callback<SuccessCheckDto>{
            override fun onResponse(
                call: Call<SuccessCheckDto>,
                response: Response<SuccessCheckDto>
            ) {
                if (response.isSuccessful){
                    Log.d("signup",response.body().toString())

                }else{
                    Log.d("fail3",response.errorBody()?.string()!!)
                }
            }

            override fun onFailure(call: Call<SuccessCheckDto>, t: Throwable) {
                Log.d("onFailure",t.localizedMessage)
            }

        })
    }
    }
    private fun validateEmail(): Boolean {
        val value: String = binding.loginEmail.text.toString()
        val emailPattern = """^[a-z0-9]*${'$'}@suwon.ac.kr"""

        return if (value.isEmpty()) {
            binding.loginEmail.error = "이메일을 입력해주세요."
            false
        } else if (!value.matches(emailPattern.toRegex())) {
            binding.loginEmail.error = "이메일 형식이 잘 못 되었습니다."
            false
        } else {
            binding.loginEmail.error = null
            true
        }
    }

    private fun validateLoginPwd(): Boolean {
        val value: String = binding.loginPwd.text.toString()
        val loginPwdPattern =  """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""

        return if (value.isEmpty()) {
            binding.loginPwd.error = "비번다시쳐."
            false
        } else if (!value.matches(loginPwdPattern.toRegex())) {
            binding.loginPwd.error = "비번 형식이 잘 못 되었습니다."
            false
        } else {
            binding.loginPwd.error = null
//            binding.loginPwd.isErrorEnabled = false
            true
        }
    }

    private fun validateLoginId(): Boolean {
        val value: String = binding.loginId.text.toString()
        val loginPattern = """^[a-z0-9]*$"""

        return if (value.isEmpty()) {
            binding.loginId.error = "아이디을 입력해주세요."
            false
        } else if (!value.matches(loginPattern.toRegex())) {
            binding.loginId.error = "아이디 형식이 잘 못 되었습니다."
            false
        } else {
            binding.loginId.error = null
//            binding.loginId.isErrorEnabled = false
            true
        }
    }
}

