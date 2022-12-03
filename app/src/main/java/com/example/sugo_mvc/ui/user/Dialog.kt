package com.example.sugo_mvc.ui.user

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.sugo_mvc.ui.adddealitem.AddItemActivity
import com.example.sugo_mvc.util.App

class dialog(context: Context) : Dialog(context) {

    fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("이미 올리셨어요")
            .setMessage("게시물은 하루에 한 개만 올릴 수 있어요")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        builder.create()
        builder.show()
    }
    fun showDialog2() {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("성공")
            .setMessage("게시물 재업로드에 성공했습니다.")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        builder.create()
        builder.show()

}
    fun showDialog3() {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("성공")
            .setMessage("메시지 전송완료")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        builder.create()
        builder.show()

    }
}