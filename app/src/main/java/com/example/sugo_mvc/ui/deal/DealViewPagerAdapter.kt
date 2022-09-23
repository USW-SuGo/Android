package com.example.sugo_mvc.ui.deal

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.R

class DealViewPagerAdapter(var dealpictures: ArrayList<Int>) :
    RecyclerView.Adapter<DealViewPagerAdapter.PagerViewHolder>() {


    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.dealrvitem, parent, false)) {
        val aespaMember = itemView.findViewById<ImageView>(R.id.dealpicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = dealpictures.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.aespaMember.setImageResource(dealpictures[position])
    }
}