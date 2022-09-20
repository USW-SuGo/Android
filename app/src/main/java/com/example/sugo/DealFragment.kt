package com.example.sugo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

    lateinit var binding: FragmentDealBinding
    //viewModels 를 사용함으로써 provider를 사용 x
    private val dealViewModel: DealViewModel by viewModels()
    val dDatas= mutableListOf<DealData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deal, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=this

        val myAdpater= DealAdapter()

        myAdpater.replaceList(dDatas)


        val dataObserver: Observer<ArrayList<DealData>> =
            Observer { DealData ->
                dDatas.apply {
                    add(
                        DealData(
                            title = "이삭",
                            pictureUrl = "dltkr",
                            place = "수원대",
                            price = "10000"
                        )
                    )
                }
                binding.dealRv.adapter=myAdpater
            }

        dealViewModel.dealData.observe(viewLifecycleOwner, dataObserver)
//        dealViewModel.getdealData().observe(viewLifecycleOwner, Observer<List<DealData>>{ dealData ->
//            binding.dealRv.adapter=myAdpater
//        })



    }

}