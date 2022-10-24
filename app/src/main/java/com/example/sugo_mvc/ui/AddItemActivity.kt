package com.example.sugo_mvc.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sugo_mvc.data.PostFormat
import com.example.sugo_mvc.data.ProductPostId
import com.example.sugo_mvc.databinding.ActivityAddItemBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.retofit.SuRetrofit
import com.example.sugo_mvc.util.App
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response


class AddItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddItemBinding.inflate(layoutInflater) }
    var list = ArrayList<Uri>()
    val adapter = AddrecycleAdapter(list, this)

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(binding.root)


                binding.pickpicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(intent,200)


            }

            binding.addsugo.setOnClickListener {
                for (i in 0 until list.size) {
                    uploadfile(list[i])
                    Log.d("sasda",uploadfile(list[i]).toString())
                }

//                val addtitle = binding.addtitle.editText?.text.toString()
//                val addprice = binding.addprice.editText?.text
//                val addcategory = binding.addcategory.editText?.text.toString()
//                val addcontactplace = binding.addcontactplace.editText?.text.toString()
//                val addcontentfield = binding.addcontentfield.editText?.text.toString()
//
//
//                RetrofitBuilder.service.postUpload(
//                    App.prefs.AccessToken.toString(),
//                    PostFormat(addtitle, addcontentfield, addprice, addcontactplace, addcategory),
//                    image = null
//                ).enqueue(object : retrofit2.Callback<ProductPostId> {
//                    override fun onResponse(
//                        call: retrofit2.Call<ProductPostId>,
//                        response: Response<ProductPostId>
//                    ) {
//                        if (response?.isSuccessful) {
//                            Log.d("로그 ", "" + response?.body().toString())
//                            Toast.makeText(applicationContext, "통신성공", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(applicationContext, "통신실패", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onFailure(call: retrofit2.Call<ProductPostId>, t: Throwable) {
//                        Log.d("onFailure", t.localizedMessage)
//                    }
//
//                })
            }
            binding.addrecycle.adapter = adapter
            binding.addrecycle.adapter = AddrecycleAdapter(list, this)
            adapter.setItemClickListener(object : AddrecycleAdapter.OnItemClickListener {
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
    private fun uploadfile(uri:Uri) {
        lifecycleScope.launch {
            val stream = contentResolver.openInputStream(uri) ?: return@launch
            val request = UploadStreamRequestBody("image/*", stream)
            Log.d("asdads",request.toString())

            val filePart = MultipartBody.Part.create(
                request
            )
//            val addtitle = binding.addtitle.editText?.text.toString()
//            val addprice = binding.addprice.editText?.text
//            val addcategory = binding.addcategory.editText?.text.toString()
//            val addcontactplace = binding.addcontactplace.editText?.text.toString()
//            val addcontentfield = binding.addcontentfield.editText?.text.toString()
//
//
//            RetrofitBuilder.service.postUpload(
//                App.prefs.AccessToken.toString(),
//                PostFormat(addtitle, addcontentfield, addprice, addcontactplace, addcategory),
//                image = filePart
//            ).enqueue(object : retrofit2.Callback<ProductPostId> {
//                override fun onResponse(
//                    call: retrofit2.Call<ProductPostId>,
//                    response: Response<ProductPostId>
//                ) {
//                    if (response?.isSuccessful) {
//                        Log.d("로그 ", "" + response?.body().toString())
//                        Toast.makeText(applicationContext, "통신성공", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(applicationContext, "통신실패", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: retrofit2.Call<ProductPostId>, t: Throwable) {
//                    Log.d("onFailure", t.localizedMessage)
//                }
//
//            })
        }


    }


}
