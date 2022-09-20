package com.example.sugo.feature.deal

import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo.DealFragment
import com.example.sugo.R
import com.example.sugo.databinding.DealrvitemBinding
import com.example.sugo.databinding.FragmentDealBinding
import com.example.sugo.feature.data.DealData

class DealAdapter : RecyclerView.Adapter<DealAdapter.MyViewHolder>(){

    private var data = mutableListOf<DealData>()

    class MyViewHolder(private val binding: DealrvitemBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(dealData: DealData){
            binding.dealpicture.text=dealData.pictureUrl
            binding.dealrvtitle.text=dealData.title
            binding.dealrvplace.text=dealData.place
            binding.dealrvprice.text=dealData.price

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:DealrvitemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_deal, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int {
       return data.size!!
    }
    fun replaceList(newList: MutableList<DealData>) {
        data = newList.toMutableList()
        //어댑터의 데이터가 변했다는 notify를 날린다
        notifyDataSetChanged()
    }

}