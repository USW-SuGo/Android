package com.example.sugo_mvc.ui.deal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.R
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.databinding.FragmentDealBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime


class DealFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private var _binding: FragmentDealBinding? = null
    private val binding get() = _binding!!
    private var page = 0
    private var size = 10
    var isLoading = false
    val accessToken = App.prefs.AccessToken!!.replace("AccessToken=", "")

    private val DealItemSize = mutableListOf<DealMainItem>()
    private lateinit var dealAdapter: DealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testItems1 = DealMainItem(0, "", "", LocalDateTime.now(), "", 0, "", "", false, false)
        binding.searchView2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {

                val text: String = binding.searchView2.getText().toString()
                dealAdapter.filter.filter(text)
                Log.d("Search", "SearchVies Text is changed : $text")
            }
        })
//        binding.searchView.setOnQueryTextListener(searchViewTextListner)
        DealItemSize.add(testItems1)
        binding.dealRv.layoutManager = GridLayoutManager(context, 2)
        dealAdapter = DealAdapter(DealItemSize)
        binding.dealRv.adapter = dealAdapter
        getMoreData()
        if (page >= 1) {
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
        binding.categorybtn.setOnClickListener {
            showPopup(binding.categorybtn)
            Log.d("btnclick", "clisk")
        }
        binding.pullContent.setOnRefreshListener {
            if (page >= 1) {
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
            binding.pullContent.isRefreshing = false
        }
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(binding.root.context, v)
        popup.menuInflater.inflate(R.menu.category, popup.menu) // ?????? ???????????? inflate
        popup.setOnMenuItemClickListener(this) // ?????? ????????? ?????? ????????? ????????????
        popup.show() // ?????? ????????????

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        when (item?.itemId) { // ?????? ???????????? ?????? ?????? ????????? ??????
            R.id.category1 -> {
                dealAdapter.filter.filter("")
                binding.categorybtn.text = "??????"
            }
            R.id.category2 -> {
                dealAdapter.filter.filter("??????")
                binding.categorybtn.text = "??????"
            }
            R.id.category3 -> {
                dealAdapter.filter.filter("????????????")
                binding.categorybtn.text = "????????????"
            }
            R.id.category4 -> {
                dealAdapter.filter.filter("????????????")
                binding.categorybtn.text = "????????????"
            }
            R.id.category5 -> {
                dealAdapter.filter.filter("??????")
                binding.categorybtn.text = "??????"
            }

        }

        return item != null // ???????????? null??? ?????? ?????? true, null??? ?????? false ??????
    }


    private fun getMoreData() {
        RetrofitBuilder.service.getItemList(page, size)
            .enqueue(object : Callback<MutableList<DealMainItem>> {
                override fun onResponse(
                    call: Call<MutableList<DealMainItem>>,
                    response: Response<MutableList<DealMainItem>>
                ) {
                    if (response.isSuccessful) {
                        val mainDealItemList = response.body()!!
                        binding.dealRv.adapter?.notifyItemInserted(DealItemSize.size - 1)
                        DealItemSize.removeAt(DealItemSize.size - 1)
                        Log.d("DealItemsize", DealItemSize.size.toString())
                        Log.d("DealItemsize", response.body()!!.size.toString())
                        for (i in 0..mainDealItemList.size - 1) {
                            DealItemSize.add(mainDealItemList[i])
                        }
                        binding.dealRv.adapter?.notifyDataSetChanged()
                        if (response.body()!!.size > 10) {
                            page += 1
                            size = response.body()!!.size - 10
                        }
                    } else {
                        Log.d("??????", "onFailure ??????: ")
                    }
                }

                override fun onFailure(call: Call<MutableList<DealMainItem>>, t: Throwable) {
                    t.localizedMessage?.let { Log.d("onFailure", it) }
                }
            })

        isLoading = false
    }


}