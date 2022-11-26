package com.example.sugo_mvc.ui

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.sugo_mvc.R

class dialog (context: Context){

    private val dialog = Dialog(context)
    fun showDialog()
    {
    dialog.setContentView(R.layout.btndialog)
    dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
    dialog.setCanceledOnTouchOutside(true)
    dialog.setCancelable(true)
    dialog.show()
}
}