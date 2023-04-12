package com.sugo.app.feat.ui.join.inputDepartment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemSelectDepartmentBinding
import com.sugo.app.feat.ui.join.inputUser.JoinViewModel

class SelectAdapter(private val viewModel: JoinViewModel) :
    ListAdapter<String, SelectAdapter.HomeBannerViewHolder>(
        BannerDiffCallback()
    ) {
    private lateinit var binding: ItemSelectDepartmentBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        binding =
            ItemSelectDepartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeBannerViewHolder(private val binding: ItemSelectDepartmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(department: String) {
            binding.viewmodel = viewModel
            binding.tvSelectDepartment.text = department
            binding.executePendingBindings()
        }
    }
}

class BannerDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}