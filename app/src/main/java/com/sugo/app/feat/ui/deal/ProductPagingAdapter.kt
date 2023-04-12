package com.sugo.app.feat.ui.deal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemDealBinding
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.DealProductDiffCallback

class ProductPagingAdapter(private val viewModel: ProductPagingViewModel) :
    PagingDataAdapter<DealProduct, ProductPagingAdapter.PagingViewHolder>(
        DealProductDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ItemDealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PagingViewHolder(private val binding: ItemDealBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dealProduct: DealProduct) {
            binding.viewModel = viewModel
            binding.dealproduct = dealProduct
            binding.executePendingBindings()
        }
    }

}