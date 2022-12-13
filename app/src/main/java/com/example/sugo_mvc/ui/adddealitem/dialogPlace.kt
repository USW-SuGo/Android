package com.example.sugo_mvc.ui.adddealitem

import android.app.Dialog
import android.view.View.inflate
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.sugo_mvc.databinding.ActivityAddItemBinding
import com.example.sugo_mvc.databinding.ActivityAddItemBinding.inflate

class dialogPlace(private val context : AppCompatActivity) {
    private lateinit var binding : ContactPlaceActivity
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    fun show(content : String) {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
         //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        binding.mDatas //부모 액티비티에서 전달 받은 텍스트 세팅
        dlg.show()
    }
}