package com.sugo.app.feat.ui.post

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sugo.app.R
import com.sugo.app.databinding.ActivityPostBinding
import com.sugo.app.feat.model.ProductPostId
import com.sugo.app.feat.model.request.Chat
import com.sugo.app.feat.model.request.ChatFile
import com.sugo.app.feat.model.response.NoteRoom
import com.sugo.app.feat.network.SugoRetrofit
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPostBinding.inflate(layoutInflater) }
/**
 *  물건 올리는 부문 리팩토링 하기
 *  spinner
 *  등등
 *
 *
 * **/

    var list = ArrayList<Uri>()
    val adapter = PostUploadAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var postCategory=""

        binding.selectPicture.setOnClickListener {
            selectGallery()
        }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.addrecycle.layoutManager = layoutManager
        binding.addrecycle.adapter = adapter

        binding.postContactPlace.setOnClickListener {
            val intent = Intent(binding.root.context, ContactPlaceActivity::class.java)
            startActivity(intent)
        }
        val contactPlace1: String = intent.getStringExtra("contactPlace").toString()
        binding.postContactPlace.text=contactPlace1

        binding.addsugo.setOnClickListener {
            val title = getBody("title", binding.postTitle.text.toString())
            val content = getBody("content", binding.postContent.text.toString())
            val price = getBody("price", binding.postPrice.editableText.toString())
            val contactPlace = getBody("contactPlace",binding.postContactPlace.text.toString())

            val postCategory = getBody("category", postCategory.toString())
            val imageMultipartBody = mutableListOf<MultipartBody.Part>()
            Log.d("image",list.toString())
            for (image in list) {
                val file = File(getRealPathFromURI(image))
                if (!file.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                    file.mkdirs()
                }
                val requestFile = RequestBody.create("image*/".toMediaTypeOrNull(), file)
                Log.d("requestFileList",requestFile.toString())
                val body =
                    MultipartBody.Part.createFormData("multipartFileList", file.name, requestFile)
                imageMultipartBody.add(body)
            }

            SugoRetrofit.tokenCreate().postUpload(
                title, content, price, contactPlace, postCategory, imageMultipartBody
            )
                .enqueue(object : retrofit2.Callback<ProductPostId> {
                    override fun onResponse(
                        call: Call<ProductPostId>,
                        response: Response<ProductPostId>
                    ) {
                        if (response.isSuccessful) {
                            finish()
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

    fun getBody(key: String, value: Any): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value.toString())
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
            val columnIndex: Int = cursor!!.getColumnIndex(columns[0])
            if (cursor.moveToFirst()) {
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
