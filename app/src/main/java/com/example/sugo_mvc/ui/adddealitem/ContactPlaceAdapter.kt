package com.example.sugo_mvc.ui.adddealitem

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.databinding.ActivityAddItemBinding
import com.example.sugo_mvc.databinding.ContactplacervitemBinding
import com.example.sugo_mvc.ui.deal.DealDetailActivity
import kotlinx.coroutines.NonDisposableHandle.parent

class ContactPlaceAdapter(var items: MutableList<String>) :
    RecyclerView.Adapter<ContactPlaceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ContactplacervitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(binding: ContactplacervitemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ContactplacervitemBinding

        fun bind(Item: String) {
             binding.rv1.text=Item
        }

        init {
            this.binding = binding

            binding.root.setOnClickListener {
                val pos = adapterPosition
                Log.d("click", "$pos : click!")
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, AddItemActivity()::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("contactPlace",   holder.binding.rv1.text)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

    }


}