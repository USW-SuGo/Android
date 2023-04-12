package com.sugo.app.feat.ui.dealdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemDetailImageBinding


class DealDetailAdapter() : ListAdapter<String, DealDetailAdapter.DealDetailViewHolder>(
    imageLinkDiffCallback()
) {
    private lateinit var binding: ItemDetailImageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealDetailViewHolder {
        binding = ItemDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DealDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DealDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DealDetailViewHolder(private val binding: ItemDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imagelink: String) {
            binding.imageUrl = imagelink
            binding.executePendingBindings()
        }
    }

    class imageLinkDiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}