package com.sugo.app.feat.ui.join

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sugo.app.feat.model.LoginId
import com.sugo.app.feat.model.email
import com.sugo.app.feat.repository.repo.join.JoinRepository
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class JoinViewModel(val joinRepository: JoinRepository) : ViewModel() {

    val _text = MutableLiveData<String>()

    //    val text: LiveData<String> = _text
    val _text2 = MutableLiveData<String>()
//    val text2: LiveData<String> = _text2

    val _pwdText = MutableLiveData<String>()
    val _pwdCheckText = MutableLiveData<String>()

    private val _introId = MutableLiveData<String>()
    val introid: LiveData<String> = _introId

    private val _introEmail = MutableLiveData<String>()
    val introEmail: LiveData<String> = _introEmail

    private val _introPwd = MutableLiveData<String>()
    val introPwd: LiveData<String> = _introPwd

    fun checkLoginId(loginId: LoginId) = viewModelScope.launch {
        val response = joinRepository.checkLoginId(loginId)
        _introId.value =
            if (response.isSuccessful && response.body()?.Exist == true) "중복된 아이디 입니다." else "사용 가능 아이디 입니다."
    }

    fun checkEmail(email: email) = viewModelScope.launch {
        val response = joinRepository.checkEmail(email)
        _introEmail.value =
            if (response.isSuccessful && response.body()?.Exist == true) "중복된 이메일 입니다." else "사용 가능 이메일 입니다."
    }

    fun checkPwd() = viewModelScope.launch {
        if (isValidPassword(_pwdText.value!!) && _pwdCheckText.value?.let { isValidPassword(it) } == true) {
            _introPwd.value = "2개의 비밀번호가 일치하지 않습니다."
            if (_pwdText.value == _pwdCheckText.value) _introPwd.value = "2개의 비밀번호가 일치합니다."
        } else _introPwd.value = "대문자와 숫자를 이용하세요"
    }

    private fun isValidPassword(input: String): Boolean {
        val pattern = "^(?=.*[!@#\$%^&*(),.?\":{}|<>])(?=.*[0-9])(?=.*[a-z]).{8,}$".toRegex()
        return pattern.matches(input)
    }

    fun onEditTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length > 5) {
                    checkEmail(email("$s@suwon.ac.kr"))
                }
            }
        }
    }

    fun onEditTextWatcherId(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.length > 5) {
                    checkLoginId(LoginId("$s"))
                }
            }
        }
    }

    fun onEditTextWatcherPwd(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                checkPwd()
            }
        }
    }
}