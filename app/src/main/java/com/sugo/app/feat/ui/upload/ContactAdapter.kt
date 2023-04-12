package com.sugo.app.feat.ui.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemPlaceBinding

class ContactAdapter(private val viewModel: UploadViewModel) :
    ListAdapter<String, ContactAdapter.ContactViewHolder>(
        BannerDiffCallback()
    ) {
    private lateinit var binding: ItemPlaceBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        binding =
            ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContactViewHolder(private val binding: ItemPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: String) {
            binding.viewmodel = viewModel
            binding.tvPlace.text = place
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