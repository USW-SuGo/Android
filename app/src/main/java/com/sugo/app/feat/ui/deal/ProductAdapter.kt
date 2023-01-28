package com.sugo.app.feat.ui.deal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemDealBinding
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.DealProductDiffCallback

class ProductAdapter(private val viewModel:DealProductViewModel) : ListAdapter<DealProduct, ProductAdapter.ProductViewHolder>(
    DealProductDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemDealBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemDealBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dealProduct: DealProduct){
//            binding.viewModel = viewModel
            binding.dealproduct=dealProduct
            binding.executePendingBindings()
        }
    }

}