package com.example.sugo_mvc.ui.deal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.R
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.databinding.FragmentDealBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime


class DealFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private var _binding: FragmentDealBinding? = null
    private val binding get() = _binding!!

    var isLoading = false


    private val DealItemSize = mutableListOf<DealMainItem>()
    private lateinit var dealAdapter: DealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        binding.searchView.setOnQueryTextListener(searchViewTextListner)
        DealItemSize.add(testItems1)
        binding.dealRv.layoutManager = GridLayoutManager(context, 2)
        dealAdapter = DealAdapter(DealItemSize)
        binding.dealRv.adapter = dealAdapter


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

        binding.categorybtn.setOnClickListener{
            showPopup(binding.categorybtn)
            Log.d("btnclick","clisk")
        }
    }
    private fun showPopup(v: View) {
        val popup = PopupMenu(binding.root.context, v)
        popup.menuInflater.inflate(R.menu.category, popup.menu) // 메뉴 레이아웃 inflate
        popup.setOnMenuItemClickListener(this) // 메뉴 아이템 클릭 리스너 달아주기
        popup.show() // 팝업 보여주기

    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {

        when (item?.itemId) { // 메뉴 아이템에 따라 동작 다르게 하기
            R.id.category1 -> {
                dealAdapter.filter.filter("")
                binding.categorybtn.text="전체"
            }
            R.id.category2 -> { dealAdapter.filter.filter("서적")
                binding.categorybtn.text="서적"}
            R.id.category3 ->{dealAdapter.filter.filter("전자기기")
                binding.categorybtn.text="전자기기"}
            R.id.category4 ->{dealAdapter.filter.filter("생활용품")
                binding.categorybtn.text="생활용품"}
            R.id.category5 ->{dealAdapter.filter.filter("기타")
                binding.categorybtn.text="기타"}

        }

        return item != null // 아이템이 null이 아닌 경우 true, null인 경우 false 리턴
    }
        var searchViewTextListner:SearchView.OnQueryTextListener=
        object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                dealAdapter.filter.filter(newText)
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
                    binding.dealRv.adapter?.notifyItemInserted(DealItemSize.size - 1)
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