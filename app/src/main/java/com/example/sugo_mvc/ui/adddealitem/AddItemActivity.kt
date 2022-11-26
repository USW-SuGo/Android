package com.example.sugo_mvc.ui.adddealitem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sugo_mvc.data.PostFormat
import com.example.sugo_mvc.databinding.ActivityAddItemBinding
import com.example.sugo_mvc.ui.UploadStreamRequestBody
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AddItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddItemBinding.inflate(layoutInflater) }
    var list = ArrayList<Uri>()
    val adapter = AddrecycleAdapter(list, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.pickpicture.setOnClickListener {
            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
            startActivityForResult(intent, 200)
        }

        binding.addsugo.setOnClickListener {

            val addtitle = binding.addtitle.editText?.text.toString()
            val addprice = binding.addprice.editText?.text.toString()
            val addcategory = binding.addcategory.text.toString()
            val list1 = ArrayList<Uri>()
            for (i in 0 until list.size) {
                list1.add(list[i])
            }
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


    private fun uploadfile(uri: ArrayList<Uri>, postFormat: PostFormat) {
        lifecycleScope.launch {
            val body = RequestBody.create(MultipartBody.FORM, "postFormat")
            val stream = contentResolver.openInputStream(uri[0]) ?: return@launch
            val request = UploadStreamRequestBody("image/*", stream)
            Log.d("asdads", request.toString())

            val filePart = MultipartBody.Part.createFormData(
                "imagelist",
                "",
                request
            )
            val listimage = arrayListOf<MultipartBody.Part>()
            listimage.add(filePart)

        }


    }


}
