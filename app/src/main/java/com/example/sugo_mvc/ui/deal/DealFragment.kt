package com.example.sugo_mvc.ui.deal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.databinding.FragmentDealBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DealFragment : Fragment() {

    private var _binding: FragmentDealBinding? = null
    private val binding get() = _binding!!

    var isLoading = false


    private val DealItemSize = mutableListOf<DealMainItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(searchViewTextListner)
        val testItems1 = DealMainItem(0, "", "", LocalDateTime.now(), "", 0, "", "",false)
        RetrofitBuilder.service.getItemList().enqueue(object : Callback<MutableList<DealMainItem>> {
            override fun onResponse(
                call: Call<MutableList<DealMainItem>>,
                response: Response<MutableList<DealMainItem>>
            ) {
                if (response.isSuccessful) {
                    Log.d("asd",call.request().toString())
                    Log.d("test",response.body()!!.toString())
//                    var mainDealItemList = mutableListOf<DealMainItem>()
                    var mainDealItemList = response.body()!!
                    Log.d("성공", " 성공: " + mainDealItemList[0].toString())
                    Log.d("성공", " 성공: " + mainDealItemList[0].updatedAt.toString())
                    testItems1.id=mainDealItemList[0].id
                    Log.d("id",testItems1.id.toString())
                    testItems1.imageLink = "https://s3.ap-northeast-2.amazonaws.com/diger-usw-sugo-s3/post-resource/%EC%A0%9C%EB%AA%A9%EC%B5%9C%EB%8C%80%EA%B8%80%EC%9E%90%EB%A5%BC%EB%AA%87%EA%B8%80%EC%9E%90%EB%A1%9C%EC%A7%80%EC%A0%95%ED%95%B4%EC%95%BC%EB%90%A0%EC%A7%80%EC%A7%80%3B%EC%83%81%EB%8B%B9%ED%9E%88%2B0"
                    testItems1.contactPlace = mainDealItemList[0].contactPlace
                    testItems1.updatedAt = mainDealItemList[0].updatedAt
                    testItems1.title = mainDealItemList[0].title
                    testItems1.price = mainDealItemList[0].price
                    testItems1.nickname = mainDealItemList[0].nickname
                    testItems1.category = mainDealItemList[0].category

                } else {
                    Log.d("성공", "onFailure 에러: ")
                }
            }

            override fun onFailure(call: Call<MutableList<DealMainItem>>, t: Throwable) {
                Log.d("onFailure", t.localizedMessage)
            }
        })
//        for(i in 1 until 10){
        DealItemSize.add(testItems1)
//        }
        binding.dealRv.layoutManager = GridLayoutManager(context, 2)
        binding.dealRv.adapter = DealAdapter(DealItemSize)

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
    var searchViewTextListner:SearchView.OnQueryTextListener=
        object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                DealAdapter(DealItemSize).filter.filter(newText)
                Log.d("Search", "SearchVies Text is changed : $newText")
                return false
            }

        }
    private fun getMoreData() {
        val testItems1 = DealMainItem(0, "", "", LocalDateTime.now(), "", 0, "", "",false)
        RetrofitBuilder.service.getItemList().enqueue(object : Callback<MutableList<DealMainItem>> {
            override fun onResponse(
                call: Call<MutableList<DealMainItem>>,
                response: Response<MutableList<DealMainItem>>
            ) {
                if (response.isSuccessful) {

                    var mainDealItemList = response.body()!!
                    for (i in 0 until response.body()!!.size){
                        testItems1.id=mainDealItemList[i].id
                        testItems1.imageLink =mainDealItemList[i].imageLink
                        testItems1.contactPlace = mainDealItemList[i].contactPlace
                        testItems1.updatedAt=mainDealItemList[i].updatedAt
                        testItems1.title = mainDealItemList[i].title
                        testItems1.price = mainDealItemList[i].price
                        testItems1.nickname = mainDealItemList[i].nickname
                        testItems1.category = mainDealItemList[i].category
                        mainDealItemList.add(testItems1)
                        Log.d("테스트아이템인덱스",testItems1.toString())
                    }
                    Log.d("testItems",DealItemSize.toString())
                    Log.d("중간",mainDealItemList.toString())
                    binding.dealRv.adapter?.notifyItemInserted(DealItemSize.size - 1)
                    Log.d("DealItemsize",DealItemSize.size.toString())
                    //순서대로 출력하기 위해

                    DealItemSize.removeAt(DealItemSize.size - 1)
                    Log.d("DealItemsize",DealItemSize.size.toString())
                        for (i in 0..mainDealItemList.size-1) {
                            DealItemSize.add(mainDealItemList[i])
                        }
                    binding.dealRv.adapter?.notifyDataSetChanged()
                } else {
                    Log.d("성공", "onFailure 에러: ")
                }
            }

            override fun onFailure(call: Call<MutableList<DealMainItem>>, t: Throwable) {
                Log.d("onFailure", t.localizedMessage)
            }
        })

        isLoading = false
    }
}