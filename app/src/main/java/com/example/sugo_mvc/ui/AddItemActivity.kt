package com.example.sugo_mvc.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sugo_mvc.data.PostFormat
import com.example.sugo_mvc.data.ProductPostId
import com.example.sugo_mvc.databinding.ActivityAddItemBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File


class AddItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddItemBinding.inflate(layoutInflater) }
    var list = ArrayList<Uri>()
    val adapter = AddrecycleAdapter(list, this)

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(binding.root)

                binding.pickpicture.setOnClickListener {
//                    getProFileImage()
                    val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    }
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
                    startActivityForResult(intent,200)
            }

            binding.addsugo.setOnClickListener {

                val addtitle = binding.addtitle.editText?.text.toString()
                val addprice = binding.addprice.editText?.text
                val addcategory = binding.addcategory.editText?.text.toString()
//                val addcontactplace = binding.addcontactplace.editText?.text.toString()
//                val addcontentfield = binding.addcontentfield.editText?.text.toString()
                val list1= ArrayList<Uri>()
                for (i in 0 until list.size) {
                    list1.add(list[i])
                }

//                uploadfile(list1,PostFormat(addtitle,addcontentfield,addprice,addcontactplace,addcategory))

//                RetrofitBuilder.service.postUpload(
//                    App.prefs.AccessToken.toString(),
//                    PostFormat(addtitle, addcontentfield, addprice, addcontactplace, addcategory),
//                    image = filePart
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

//
        if (resultCode == RESULT_OK && requestCode == 200 ) {
            list.clear()
            val imagePath = data!!.data
            val file = File(absolutelyPath(imagePath, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("proFile", file.name, requestFile)
            val addtitle = binding.addtitle.editText?.text.toString()
            val addprice = binding.addprice.editText?.text
            val addcategory = binding.addcategory.editText?.text.toString()
//            val addcontactplace = binding.addcontactplace.editText?.text.to/
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
//                        sendImage(body,PostFormat(addtitle, addcontentfield, addprice, addcontactplace, addcategory))
                    }
                }
            }
            binding.addrecycle.adapter?.notifyDataSetChanged()

        }

    }
    private fun uploadfile(uri: ArrayList<Uri>, postFormat: PostFormat) {
        lifecycleScope.launch {
            val body=RequestBody.create(MultipartBody.FORM,"postFormat")
            val stream = contentResolver.openInputStream(uri[0]) ?: return@launch
            val request = UploadStreamRequestBody("image/*", stream)
            Log.d("asdads",request.toString())

            val filePart = MultipartBody.Part.createFormData(
                "imagelist",
                "",
                request
            )
            val listimage= arrayListOf<MultipartBody.Part>()
            listimage.add(filePart)
//            RetrofitBuilder.service.postUpload(
//                App.prefs.AccessToken.toString(),
//                image  ,
//                postFormat
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

    fun getProFileImage(){

//        startActivityForResult(intent,200)
        Log.d(TAG,"사진변경 호출")
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
        launcher.launch(chooserIntent)
    }
    fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = c?.getString(index!!)
        return result!!
    }
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK &&result.resultCode == 200) {
            list.clear()

            if (result.data?.clipData != null) { // 사진 여러개 선택한 경우

                val count = result.data!!.clipData!!.itemCount

                if (count > 5) {
                    Toast.makeText(applicationContext, "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    val imagePath = result.data!!.data
                    val file = File(absolutelyPath(imagePath, this))
                    val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                    val body = MultipartBody.Part.createFormData("proFile", file.name, requestFile)
                    Log.d(TAG, file.name)
//                    val addtitle = binding.addtitle.editText?.text.toString()
//                    val addprice = binding.addprice.editText?.text
//                    val addcategory = binding.addcategory.editText?.text.toString()
//                    val addcontactplace = binding.addcontactplace.editText?.text.toString()
//                    val addcontentfield = binding.addcontentfield.editText?.text.toString()
//                    sendImage(body, PostFormat(addtitle,addcontentfield,addprice,addcontactplace,addcategory))
                    return@registerForActivityResult

                    for (i in 0 until count) {
                        val imageUri = result.data!!.clipData!!.getItemAt(i).uri
                        list.add(imageUri)

                    }
                }
            } else { // 단일 선택
                result.data?.data?.let { uri ->
                    val imageUri : Uri? = result.data?.data
                    if (imageUri != null) {
                        list.add(imageUri)

                    }
                }
            }
            binding.addrecycle.adapter?.notifyDataSetChanged()


        }


}


//    private fun sendImage(image : MultipartBody.Part,postFormat:PostFormat) {
//
//        RetrofitBuilder.service.postUpload(
//            App.prefs.AccessToken.toString(),
//            image,
//            postFormat
//        ).enqueue(object : retrofit2.Callback<ProductPostId> {
//            override fun onResponse(
//                call: retrofit2.Call<ProductPostId>,
//                response: Response<ProductPostId>
//            ) {
//                if (response?.isSuccessful) {
//                    Log.d("로그 ", "" + response?.body().toString())
//                    Toast.makeText(applicationContext, "통신성공", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(applicationContext, "통신실패", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: retrofit2.Call<ProductPostId>, t: Throwable) {
//                Log.d("onFailure", t.localizedMessage)
//            }
//
//        })
//    }
}