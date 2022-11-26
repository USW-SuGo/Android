package com.example.sugo_mvc.ui.user

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sugo_mvc.R
import com.example.sugo_mvc.databinding.ActivityMainBinding
import com.example.sugo_mvc.databinding.BtndialogBinding

class dialog(context: Context) : Dialog(context) {
    private val binding by lazy { BtndialogBinding.inflate(layoutInflater) }
    private val dialog = Dialog(context)


    fun showDialog() {
        
        dialog.setContentView(R.layout.btndialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()
        val noButton = binding.okBtn
       noButton.setOnClickListener {
            Log.d("click","click")
            dialog.dismiss()
        }
    }
    
}