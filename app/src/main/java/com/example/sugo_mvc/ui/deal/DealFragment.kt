package com.example.sugo_mvc.ui.deal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.LectureMain
import com.example.sugo_mvc.data.dataDto
import com.example.sugo_mvc.databinding.FragmentDealBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DealFragment: Fragment() {

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
        val testItems1= DealMainItem(0,"","",null,"",0,"","")
        testItems1.id=0
        testItems1.imageLink=""
        testItems1.contactPlace="asdad"
        testItems1.updateAt
        testItems1.title="dltkr"
        testItems1.price=10000
        testItems1.nickname=""
        testItems1.category=""

        for(i in 1 until 10){
            DealItem.add(testItems1)
        }
        binding.dealRv.layoutManager = GridLayoutManager(context, 2)
        binding.dealRv.adapter = DealAdapter(DealItem)

        binding.dealRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.dealRv.layoutManager as LinearLayoutManager
                if(!isLoading){
                    if(!binding.dealRv.canScrollVertically(1)){
                        isLoading = true
                        getMoreData()

                    }
                }
            }
        })


    }

    private fun getMoreData() {
//        var mainItemList :MutableList<DealMainItem?>?
//        RetrofitBuilder.service.getItemList().enqueue(object : Callback <MutableList<DealMainItem?>> {
//            override fun onResponse(
//                call: Call<MutableList<DealMainItem?>>,
//                response: Response<MutableList<DealMainItem?>>
//            ) {
//                response.takeIf { it.isSuccessful }
//                    ?.body()
//                    ?.let { it ->
//                        // do something
//                        mainItemList = response.body()
//                        Log.d("CertifTabFragment", mainItemList.toString())
//
//                        //인증한 adapter에 Member 데이터 넣기
//
//                    }
//            }

//            override fun onFailure(call: Call<MutableList<DealMainItem?>>, t: Throwable) {
//                Log.d("onFailure",t.localizedMessage)
//            }
//
//
//        })
        val testItems1= DealMainItem(0,"","",null,"",0,"","")
        testItems1.id=0
        testItems1.imageLink=""
        testItems1.contactPlace="asdad"
        testItems1.updateAt
        testItems1.title="dltkr"
        testItems1.price=10000
        testItems1.nickname=""
        testItems1.category=""
        DealItem.add(testItems1)
        binding.dealRv.adapter?.notifyItemInserted(DealItem.size - 1)
        DealItem.removeAt(DealItem.size - 1)
        val currentSize = DealItem.size
        for(i in currentSize+1 until currentSize+10){
            DealItem.add(testItems1)
        }
        binding.dealRv.adapter?.notifyDataSetChanged()
        isLoading = false
    }
}