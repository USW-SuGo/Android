package com.example.sugo_mvc.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.sugo_mvc.MainActivity
import com.example.sugo_mvc.R
import com.example.sugo_mvc.data.*


import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.ui.JoinActivity
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { com.example.sugo_mvc.databinding.ActivityLoginBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.signupbtn.setOnClickListener {

            val intent = Intent(applicationContext, JoinActivity::class.java)
            startActivity(intent)

        }

        binding.loginBtn.setOnClickListener {
            val bundle = Bundle()

            val loginid1 = binding.loginId1.text.toString()
            bundle.putString("id", loginid1)
            val loginpwd2 = binding.loginPWD2.text.toString()
            Log.d("good", loginid1 + loginpwd2)

            RetrofitBuilder.service.login(LoginFormat(loginid1, loginpwd2))
                .enqueue(object : Callback<Token> {
                    override fun onResponse(
                        call: Call<Token>,
                        response: Response<Token>
                    ) {
                        if (response.isSuccessful) {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            val result = response.headers().get("Authorization")
                            val spi = result!!.split(",")
                            App.prefs.AccessToken = spi[1].replace("}", "")
                            App.prefs.RefreshToken=spi[0].replace("{","")
                            Log.d("LoginSuccess", spi.toString()!!)

                        } else {
                            Log.d("LoginFail", response.errorBody()?.string()!!)
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Log.d("onFailure", t.localizedMessage)
                    }
                })
        }
            binding.findidbtn.setOnClickListener {
                val intent = Intent(applicationContext, findIdActivity::class.java)
                startActivity(intent)
            }

            binding.findpwdbtn.setOnClickListener {
                val intent = Intent(applicationContext, FindPwdActivity::class.java)
                startActivity(intent)
            }

    }


}