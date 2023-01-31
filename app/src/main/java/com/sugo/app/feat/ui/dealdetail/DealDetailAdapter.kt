package com.sugo.app.feat.ui.dealdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemDealBinding
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.DealProductDiffCallback

class DealDetailAdapter: ListAdapter<DealProduct, DealDetailAdapter.DealDetailViewHolder>(
    DealProductDiffCallback()
) {
    private lateinit var binding: ItemDealBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealDetailViewHolder {
        binding=ItemDealBinding.inflate(LayoutInflater.from(parent.context),false)
        return DealDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DealDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DealDetailViewHolder(private val binding : ItemDealBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(dealProduct: DealProduct){

        }
    }
}