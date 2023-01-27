package com.example.sugo_mvc.ui.adddealitem

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.Cursor.FIELD_TYPE_STRING
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugo_mvc.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var postCategory=""
//        val itemList = listOf("category","서적","전자기기","생활용품","기타")
        val data:Array<String> = resources.getStringArray(R.array.itemList)
        val adapter2 = ArrayAdapter(this, R.layout.spinneritem, data)
        binding.spinner.adapter = adapter2
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if(position != 0)
                    postCategory=data[position]
            }
//
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        binding.selectPicture.setOnClickListener {
            selectGallery()
        }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.addrecycle.layoutManager = layoutManager
        binding.addrecycle.adapter = adapter
//        binding.postCategory.setOnClickListener {
//            showPopup(binding.root)
//        }
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
            for (image in list) {
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
                title, content, price, contactPlace, postCategory, imageMultipartBody
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
    val mDatas=mutableListOf<String>()
    fun initRecyclerView(){
        val adapter=ContactPlaceAdapter(mDatas) //어댑터 객체 만듦
//        binding.contactrv.adapter=adapter //리사이클러뷰에 어댑터 연결
//        binding.contactrv.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결
    }

    fun initializelist(){ //임의로 데이터 넣어서 만들어봄
        with(mDatas){
            add("정문")
            add("인문대")
            add("체대")
            add("미래혁신관")
            add("공대")
            add("IT")
            add("ACE교육관 (구 종합강의동)")
            add("학생회관")
            add("건강과학대학")
            add("중앙도서관")
            add("글로벌경상대")

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

//    private fun showPopup(v: View) {
//        val popup = PopupMenu(binding.root.context, v)
//        popup.menuInflater.inflate(R.menu.category, popup.menu) // 메뉴 레이아웃 inflate
//        popup.setOnMenuItemClickListener(this) // 메뉴 아이템 클릭 리스너 달아주기
//        popup.show() // 팝업 보여주기
//
//    }
//
//    override fun onMenuItemClick(item: MenuItem?): Boolean {
//
//        when (item?.itemId) { // 메뉴 아이템에 따라 동작 다르게 하기
//            R.id.category1 -> {
//                binding.postCategory.text = "전체"
//            }
//            R.id.category2 -> {
//                binding.postCategory.text = "서적"
//            }
//            R.id.category3 -> {
//                binding.postCategory.text = "전자기기"
//            }
//            R.id.category4 -> {
//                binding.postCategory.text = "생활용품"
//            }
//            R.id.category5 -> {
//                binding.postCategory.text = "기타"
//            }
//
//        }
//
//        return item != null // 아이템이 null이 아닌 경우 true, null인 경우 false 리턴
//    }
}






