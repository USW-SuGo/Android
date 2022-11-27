package com.example.sugo_mvc.ui.adddealitem

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.Cursor.FIELD_TYPE_STRING
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddItemBinding.inflate(layoutInflater) }
    val accessToken = App.prefs.AccessToken!!.replace("AccessToken=", "")

    var list = ArrayList<Uri>()
    val adapter = AddrecycleAdapter(list)
    val uri=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.pickpicture.setOnClickListener {
            selectGallery()

        }
        Log.d("image",list.toString())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.addrecycle.layoutManager = layoutManager
        binding.addrecycle.adapter = adapter

        binding.addsugo.setOnClickListener {

            val addtitle = binding.addtitle.editText?.text.toString()
            val addcontent = "asdasd"
            val addprice = binding.addprice.editText?.text.toString()
            val addcontactPlace = "asdasd"
            val addcategory = binding.addcategory.text.toString()

            }
    }

    private var currentPhotoPath:String=""
    var mImageCaptureName:String=""
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
                    currentPhotoPath=getFilePathFromUri(imageUri)
                    val file = File(getFilePathFromUri(imageUri))
                    if (!file.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                        file.mkdirs();    // 하위폴더를 포함한 폴더를 전부 생성
                    }
                    val requestFile = RequestBody.create(MediaType.parse("image*"),createImageFile())
                    val body = MultipartBody.Part.createFormData("proFile", file.name, requestFile)
                    RetrofitBuilder.service
                        .postUpload(
                            accessToken,
                            PostFormat(
                            "test",
                                "test",
                                3,
                                "test",
                                "test"
                            ),
                            body
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
                    Log.d("image",getFilePathFromUri(imageUri).toString())
                    list.add(imageUri)
                }

            } else { // 단일 선택
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

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val dir = File(Environment.getExternalStorageDirectory().toString() + "/path/")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        mImageCaptureName = "$timeStamp.png"
        val storageDir = File(
            Environment.getExternalStorageDirectory().absoluteFile.toString() + "/path/"
                    + mImageCaptureName
        )
        currentPhotoPath = storageDir.absolutePath
        return storageDir
    }
    fun getFilePathFromUri(uri: Uri): String {

        var cursor : Cursor? = null
        var result: String =""

        try {
            val projection = arrayOf(MediaStore.Images.Media.DATA)

            cursor = contentResolver.query(uri, projection, null, null, null)
            if (cursor == null) {
                result = uri.path!!
            } else {
                Log.d("1", "Path_img = " + uri)
                Log.d("2", "Path_pth = " + uri.path)
                Log.d("3", "Path_cursor = " + cursor)

                cursor.moveToFirst()
                val idx = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)
                if (cursor.getType(idx) == FIELD_TYPE_STRING) {
                    result = cursor.getString(idx);
                }
            }

            return result
        } finally {
            cursor!!.close()
        }
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
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*"
            )

            imageResult.launch(intent)
        }
    }
}



