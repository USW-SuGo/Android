package com.example.sugo_mvc.ui.deal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.sugo_mvc.R
import com.example.sugo_mvc.data.DealDetailItem
import com.example.sugo_mvc.data.Like
import com.example.sugo_mvc.data.ProductPostId
import com.example.sugo_mvc.databinding.ActivityDealDetailBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Duration
import java.time.LocalDateTime

class DealDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDealDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id: String? = intent.getStringExtra("id")
        val userLikeStatus: String? = intent.getStringExtra("userLikeStatus")
        Log.d("userlike",userLikeStatus.toString())

        Log.d("id", id.toString())
           var imageList: List<String>
        val accessToken = App.prefs.AccessToken!!.replace("AccessToken=", "")
        var imagelink: String
        Log.d("token", accessToken)
//        Log.d("token",idd)
        RetrofitBuilder.service.getDetailPage(accessToken, id!!.toLong())
            .enqueue(object : Callback<DealDetailItem> {
                override fun onResponse(
                    call: Call<DealDetailItem>,
                    response: Response<DealDetailItem>
                ) {
                    imagelink = response.body()?.imageLink?.replace("[", "")
                        ?.replace("]", "")!!.replace(" ", "")
                    imageList = imagelink.split(",")
                    var a=""
                    val startDateTime = LocalDateTime.now()
                    val endDateTime = response.body()!!.updatedAt
                    val duration: Duration = Duration.between(startDateTime, endDateTime)
                    Log.d("duration",duration.toDays().toString().replace("-",""))
                    if(duration.toDays().toString().replace("-","")=="0")  a="오늘"
                    if(duration.toDays().toString().replace("-","")=="1")  a="어제"
                    if(duration.toDays().toString().replace("-","")!="1"&&
                        duration.toDays().toString().replace("-","")!="0")  a=duration.toDays().toString().replace("-","")+"일 전"

                    binding.dealViewPager.adapter = DealViewPagerAdapter(imageList)
                    binding.dealViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                    Log.d("datetime",response.body()!!.updatedAt.toString())
                    binding.dealrvplace.text = response.body()!!.contactPlace+" | "+a+" | "+response.body()!!.category
                    binding.dealrvtitle.text = response.body()!!.title

                    binding.dealNickname.text = response.body()!!.nickname
                    binding.dealrvprice.text = response.body()!!.price.toString()+"원"
                    binding.content.text = response.body()!!.content
                    Log.d("asd", call.request().toString())
                    Log.d("asd", response.body()!!.userLikeStatus.toString())

                    if (response.body()!!.userLikeStatus.toString().contains("true")) {
                        binding.likebtn.setImageResource(R.drawable.heartred)
                    }
                    if (response.body()!!.userLikeStatus.toString().contains("false")) {
                        binding.likebtn.setImageResource(R.drawable.heart)
                    }
                }

                override fun onFailure(call: Call<DealDetailItem>, t: Throwable) {
                    Log.d("asd", "error")
                }

            })

        binding.likebtn.setOnClickListener {
            RetrofitBuilder.service.like(accessToken, ProductPostId(id.toLong()))
                .enqueue(object : Callback<Like> {
                    override fun onResponse(call: Call<Like>, response: Response<Like>) {
                        if (response.isSuccessful) {
                            Log.d("like", response.body().toString())
                            if (response.body().toString() == "Like(Like=true)") {
                                binding.likebtn.setImageResource(R.drawable.heartred)
                            }
                            if (response.body().toString() == "Like(Like=false)") {
                                binding.likebtn.setImageResource(R.drawable.heart)
                            }
                        } else {
                            Log.d("likefail", response.errorBody().toString())
                        }
                    }

                    override fun onFailure(call: Call<Like>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }

    }
}