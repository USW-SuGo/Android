package com.sugo.app.feat.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemMypageBinding
import com.sugo.app.databinding.ItemUserPageBinding
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.DealProductDiffCallback

class UserAdapter(
    private val viewModel: UserViewModel,
) : PagingDataAdapter<DealProduct, UserAdapter.PagingViewHolder>(
    DealProductDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ItemUserPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PagingViewHolder(private val binding: ItemUserPageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dealProduct: DealProduct) {
            binding.viewModel = viewModel
            binding.dealproduct = dealProduct
            binding.executePendingBindings()

        }
    }

}