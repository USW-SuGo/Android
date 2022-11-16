package com.example.sugo_mvc.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sugo_mvc.MainActivity
import com.example.sugo_mvc.R
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.LoginFormat
import com.example.sugo_mvc.data.Token
import com.example.sugo_mvc.databinding.ActivityLoginBinding

import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.ui.JoinActivity
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.signupbtn.setOnClickListener {
            val intent = Intent(applicationContext, JoinActivity::class.java)
            startActivity(intent)

        }

        binding.loginBtn.setOnClickListener {

            val loginid1=binding.loginId1.text.toString()
            val loginpwd2=binding.loginPWD2.text.toString()
            Log.d("good",loginid1+loginpwd2)

            RetrofitBuilder.service.login(LoginFormat(loginid1,loginpwd2)).enqueue(object :Callback<Token>{
                override fun onResponse(
                    call: Call<Token>,
                    response: Response<Token>) {
                    val result= response.headers().get("Authorization")

                    if (response.isSuccessful){
                        val spi =result!!.split(",")
                        App.prefs.AccessToken=spi[1].replace("}","")
                        App.prefs.RefreshToken=spi[0].replace("{","")
                        val token = App.prefs.AccessToken
                        val token2 = App.prefs.RefreshToken
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)

                    }else{
                        Log.d("LoginFail",response.errorBody()?.string()!!)
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Log.d("onFailure",t.localizedMessage)
                }
            })
            binding.findidbtn.setOnClickListener {
                
            }

            binding.findpwdbtn.setOnClickListener {  }
        }
    }
}