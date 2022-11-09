package com.example.sugo_mvc.ui.deal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.MainActivity
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.LoginFormat
import com.example.sugo_mvc.data.Token
import com.example.sugo_mvc.databinding.FragmentDealBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime


class DealFragment : Fragment() {

    private var _binding: FragmentDealBinding? = null
    private val binding get() = _binding!!

    var isLoading = false
    private val DealItem = mutableListOf<DealMainItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testItems1 = DealMainItem(0, "", "", "", "", 0, "", "")
        testItems1.id = 0
        testItems1.imageLink = ""
        testItems1.contactPlace = "asdad"
        testItems1.updateAt = ""
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            testItems1.updateAt= LocalDateTime.of(2022,1,26,19,30,20)
//        }
        testItems1.title = "dltkr"
        testItems1.price = 10000
        testItems1.nickname = ""
        testItems1.category = ""


//        RetrofitBuilder.service.getItemList().enqueue(object : Callback<MutableList<DealMainItem>> {
//            override fun onResponse(
//                call: Call<MutableList<DealMainItem>>,
//                response: Response<MutableList<DealMainItem>>
//            ) {
//                if (response.isSuccessful) {
//                    var mainDealItemList = mutableListOf<DealMainItem>()
//                    mainDealItemList = response.body()!!
//                    Log.d("성공", " 성공: " + mainDealItemList[0].toString())
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
//        for(i in 1 until 10){
        DealItem.add(testItems1)
//        }
        binding.dealRv.layoutManager = GridLayoutManager(context, 2)
        binding.dealRv.adapter = DealAdapter(DealItem)

        binding.dealRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.dealRv.layoutManager as LinearLayoutManager
                if (!isLoading) {
                    if (!binding.dealRv.canScrollVertically(1)) {
                        isLoading = true
                        getMoreData()

                    }
                }
            }
        })


    }

    private fun getMoreData() {
        val testItems1 = DealMainItem(0, "", "", "null", "", 0, "", "")
        testItems1.id = 0
        testItems1.imageLink = ""
        testItems1.contactPlace = "asdad"
        testItems1.updateAt
        testItems1.title = "dltkr"
        testItems1.price = 10000
        testItems1.nickname = ""
        testItems1.category = ""
        DealItem.add(testItems1)
        binding.dealRv.adapter?.notifyItemInserted(DealItem.size - 1)
        DealItem.removeAt(DealItem.size - 1)
        val currentSize = DealItem.size
        for (i in currentSize + 1 until currentSize + 10) {
            DealItem.add(testItems1)
        }
        binding.dealRv.adapter?.notifyDataSetChanged()
        isLoading = false
    }
}