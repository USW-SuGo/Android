package com.example.sugo.feature.deal

import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

import com.example.sugo.R
import com.example.sugo.databinding.DealrvitemBinding
import com.example.sugo.databinding.FragmentDealBinding
import com.example.sugo.feature.data.DealData
import kotlinx.android.synthetic.main.fragment_deal.view.*
class DealAdapter(val items: MutableList<DealData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM = 1
        const val LOADING = 0
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: DealData) {
            val message = itemView.findViewById<TextView>(R.id.dealrvtitle)
            message.text = item.pictureUrl
            val message1 = itemView.findViewById<TextView>(R.id.dealpicture)
            message1.text = item.place
            val message2 = itemView.findViewById<TextView>(R.id.dealrvprice)
            message2.text = item.price
            val message3 = itemView.findViewById<TextView>(R.id.dealrvplace)
            message3.text = item.title
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar = itemView.findViewById<ProgressBar>(R.id.rv_loading_pb)!!
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) {
            ITEM
        } else {
            LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.dealrvitem, parent, false)
            ItemViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.rv_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bindItems(items[position]!!)
        } else {

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}