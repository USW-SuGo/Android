package com.example.sugo_mvc.ui.adddealitem

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugo_mvc.data.PostFormat
import com.example.sugo_mvc.data.ProductPostId
import com.example.sugo_mvc.databinding.ActivityAddItemBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


class AddItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddItemBinding.inflate(layoutInflater) }
    val accessToken = App.prefs.AccessToken!!.replace("AccessToken=", "")

    var list = ArrayList<Uri>()
    val adapter = AddrecycleAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.pickpicture.setOnClickListener {
            selectGallery()

        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.addrecycle.layoutManager = layoutManager
        binding.addrecycle.adapter = adapter

        binding.addsugo.setOnClickListener {

            val addtitle = binding.addtitle.editText?.text.toString()
            val addcontent = "binding"
            val addprice = binding.addprice.editText?.text.toString()
            val addcontactPlace = "미래혁신관"
            val addcategory = "서적"
            val imageMultipartBody = mutableListOf<MultipartBody.Part>()
            for(image in list){
                val file = File(getRealPathFromURI(image))
                if (!file.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                    file.mkdirs()
                }
                val requestFile = RequestBody.create(MediaType.parse("image*/"), file)
                val body =
                    MultipartBody.Part.createFormData("multipartFileList", file.name, requestFile)
                imageMultipartBody.add(body)
            }
            RetrofitBuilder.service.postUpload(
                accessToken, PostFormat(
                    addtitle,
                    addcontent, addprice.toLong(),
                    addcontactPlace,
                    addcategory
                ), imageMultipartBody
            )
                .enqueue(object : retrofit2.Callback<ProductPostId> {
                    override fun onResponse(
                        call: Call<ProductPostId>,
                        response: Response<ProductPostId>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("postUploadtest", response.body().toString())
                        } else {
                            Log.d("postUploadFail", response.errorBody()!!.toString())
                        }
                    }

                    override fun onFailure(call: Call<ProductPostId>, t: Throwable) {
                        Log.d("postUploadFail", t.toString())
                    }

                })
        }
    }

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {

            list.clear()

            if (it.data?.clipData != null) { // 사진 여러개 선택한 경우
                val count = it.data!!.clipData!!.itemCount
                if (count > 2) {
                    Toast.makeText(applicationContext, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                }
                for (i in 0 until count) {
                    val imageUri = it.data!!.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }

            } else {
                it.data?.data?.let { uri ->
                    val imageUri: Uri? = it.data?.data
                    if (imageUri != null) {
                        list.add(imageUri)

                    }
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        if (contentUri.path!!.startsWith("/storage")) {
            return contentUri.path
        }
        val id = DocumentsContract.getDocumentId(contentUri).split(":").toTypedArray()[1]
        val columns = arrayOf(MediaStore.Files.FileColumns.DATA)
        val selection = MediaStore.Files.FileColumns._ID + " = " + id
        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            columns,
            selection,
            null,
            null
        )
        try {
            val columnIndex = cursor!!.getColumnIndex(columns[0])
            if (cursor.moveToFirst()) {
                Log.d("elseresulr", cursor.getString(columnIndex).toString())
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor!!.close()
        }
        return null
    }

    private fun selectGallery() {
        val writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1
            )
        } else {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
            )

            imageResult.launch(intent)
        }
    }
}



