package com.example.sugo_mvc.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugo_mvc.databinding.ActivityAddItemBinding


class AddItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddItemBinding.inflate(layoutInflater)}

    var list = ArrayList<Uri>()
    val adapter=AddrecycleAdapter(list,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.pickpicture.setOnClickListener {
//            var intent =Intent(Intent.ACTION_GET_CONTENT)
////            var intent=Intent
//            intent.setType("image/*")
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            intent.action=Intent.ACTION_GET_CONTENT
//
//            startActivityForResult(intent,200)


// 버튼을 누르면 해당 Intent 를 호출하는데,
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(intent,200)

        }
        binding.addrecycle.adapter=adapter
        binding.addrecycle.adapter = AddrecycleAdapter(list,this)
        adapter.setItemClickListener(object : AddrecycleAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
               list.removeAt(position)
                binding.addrecycle.adapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == RESULT_OK && requestCode == 200) {
            list.clear()

            if (data?.clipData != null) { // 사진 여러개 선택한 경우
                val count = data.clipData!!.itemCount
                if (count > 5) {
                    Toast.makeText(applicationContext, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    return
                }
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }

            } else { // 단일 선택
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if (imageUri != null) {
                        list.add(imageUri)
                    }
                }
            }
            binding.addrecycle.adapter?.notifyDataSetChanged()

        }

    }
}
