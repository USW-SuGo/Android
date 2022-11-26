package com.example.sugo_mvc.ui.deal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.sugo_mvc.data.DealDetailItem
import com.example.sugo_mvc.databinding.ActivityDealDetailBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DealDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDealDetailBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id: String? = intent.getStringExtra("id")
        Log.d("id",id.toString())
        val testItems1 = DealDetailItem(0, 0, "", "", "LocalDateTime.now()", "", "", 0,"","",false,false)
        var imageList :List<String>
        val accessToken =App.prefs.AccessToken!!.replace("AccessToken=","")
        var imagelink:String
        Log.d("token",accessToken)
//        Log.d("token",idd)
        RetrofitBuilder.service.getDetailPage(accessToken,id!!.toLong())
            .enqueue( object : Callback<DealDetailItem>{
            override fun onResponse(
                call: Call<DealDetailItem>,
                response: Response<DealDetailItem>
            ) {
                imagelink=response.body()?.imageLink?.replace("[","")
                    ?.replace("]","")!!.replace(" ","")
                imageList=imagelink.split(",")
                binding.dealViewPager.adapter = DealViewPagerAdapter(imageList)
                binding.dealViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//                binding.=response.body()!!.id
                testItems1.imageLink =response.body()!!.imageLink
                testItems1.contactPlace = response.body()!!.contactPlace
//                testItems1.updateAt=response.body()!!.updateAt
                binding.dealrvtitle.text= response.body()!!.title
                binding.dealrvplace.text = response.body()!!.contactPlace
                binding.dealNickname.text = response.body()!!.nickname
                binding.dealCategory.text = response.body()!!.category
                binding.dealrvprice.text=response.body()!!.price.toString()
                binding.content.text=response.body()!!.content
                    Log.d("asd", call.request().toString())
                    Log.d("asd",response.body().toString())
                }
            override fun onFailure(call: Call<DealDetailItem>, t: Throwable) {
                Log.d("asd","error")
            }

        })

    }
}