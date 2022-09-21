package com.example.sugo.feature.deal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo.R
import com.example.sugo.databinding.FragmentDealBinding
import com.example.sugo.feature.data.DealData
import com.example.sugo.feature.deal.DealAdapter
import com.example.sugo.feature.deal.DealViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DealFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DealFragment:Fragment() {

    lateinit var binding:FragmentDealBinding
    private lateinit var dealAdapter: DealAdapter
    //adapter
    var isLoading = false
    private val testItems = mutableListOf<DealData>()
    //
    //viewModels 를 사용함으로써 provider를 사용 x
    private val dealViewModel: DealViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deal, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testItems1=DealData()
        testItems1.title="dltkr"
        testItems1.pictureUrl="wwww"
        testItems1.place="asdad"
        testItems1.price="00121"
        for(i in 1 until 10){
            testItems.add(testItems1)
        }
        binding.dealRv.layoutManager = GridLayoutManager(context, 2)
        binding.dealRv.adapter = DealAdapter(testItems)
        binding.dealRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.dealRv.layoutManager as LinearLayoutManager
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
        val testItems1=DealData()
        testItems1.title="dltkr"
        testItems1.pictureUrl="wwww"
        testItems1.place="asdad"
        testItems1.price="00121"
        testItems.add(testItems1)
        binding.dealRv.adapter?.notifyItemInserted(testItems.size - 1)
        testItems.removeAt(testItems.size - 1)
        val currentSize = testItems.size
        for(i in currentSize+1 until currentSize+10){
            testItems.add(testItems1)
        }
        binding.dealRv.adapter?.notifyDataSetChanged()
        isLoading = false
    }
}