package com.example.sugo_mvc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.example.sugo_mvc.data.DealDetailItem

import com.example.sugo_mvc.databinding.ActivityDealDetailBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.ui.deal.DealData
import com.example.sugo_mvc.util.App
import com.example.sugo_mvc.util.Constants.Companion.BASE_URL
import com.example.sugo_mvc.util.Constants.Companion.DETAILPAGE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DealDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDealDetailBinding.inflate(layoutInflater)}
    private val dealPicture= arrayListOf<DealData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val testItems1 = DealDetailItem(0, 0, "", "", "", "", "", 0,"","",false,false)
        var imageList :List<String>
        val accessToken =App.prefs.AccessToken!!.replace("AccessToken=","")
        Log.d("token",accessToken)
        RetrofitBuilder.service.getDetailPage(accessToken)
            .enqueue( object : Callback<DealDetailItem>{
            override fun onResponse(
                call: Call<DealDetailItem>,
                response: Response<DealDetailItem>
            ) {
                    Log.d("asd", call.request().toString())
                }

            override fun onFailure(call: Call<DealDetailItem>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

//                testItems1.id=response.body()!!.id
//                testItems1.imageLink =response.body()!!.imageLink
//                testItems1.contactPlace = response.body()!!.contactPlace
//                testItems1.updateAt=response.body()!!.updateAt
//                testItems1.title = response.body()!!.title
//                testItems1.price = response.body()!!.price
//                testItems1.nickname = response.body()!!.nickname
//                testItems1.category = response.body()!!.category
//                testItems1.status=response.body()!!.status
//                mainDealItemList.add(testItems1)

//        RetrofitBuilder.service.getItemList().enqueue(object : Callback<MutableList<DealMainItem>> {
//                        override fun onResponse(
//                            call: Call<MutableList<DealMainItem>>,
//                            response: Response<MutableList<DealMainItem>>
//            ) {
//                if (response.isSuccessful) {
//                    mainDealItemList = response.body()!!
//                    Log.d("성공", " 성공: " + mainDealItemList[0].toString())
//                    Log.d("성공", " 성공: " + mainDealItemList[0].imageLink)
//                    aas=mainDealItemList[0].imageLink.replace("[","")
//                        .replace("]","").replace(" ","")
//                    imageList=aas.split(",")
//                    Log.d("성공", " 성공: " + imageList)
//                    binding.dealViewPager.adapter = DealViewPagerAdapter(imageList)
//                    binding.dealViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//                    testItems1.id=mainDealItemList[0].id
//                    testItems1.imageLink = "https://s3.ap-northeast-2.amazonaws.com/diger-usw-sugo-s3/post-resource/%EC%A0%9C%EB%AA%A9%EC%B5%9C%EB%8C%80%EA%B8%80%EC%9E%90%EB%A5%BC%EB%AA%87%EA%B8%80%EC%9E%90%EB%A1%9C%EC%A7%80%EC%A0%95%ED%95%B4%EC%95%BC%EB%90%A0%EC%A7%80%EC%A7%80%3B%EC%83%81%EB%8B%B9%ED%9E%88%2B0"
//                    testItems1.contactPlace = mainDealItemList[0].contactPlace
////                    testItems1.updateAt = mainDealItemList[0].updateAt
//                    testItems1.updateAt="201"
//                    testItems1.title = mainDealItemList[0].title
//                    testItems1.price = mainDealItemList[0].price
//                    testItems1.nickname = mainDealItemList[0].nickname
//                    testItems1.category = mainDealItemList[0].category
//
//                } else {
//                    Log.d("성공", "onFailure 에러: ")
//                }
//            }
//
//            override fun onFailure(call: Call<MutableList<DealMainItem>>, t: Throwable) {
//                Log.d("onFailure", t.localizedMessage)
//            }
//        })
    }
}