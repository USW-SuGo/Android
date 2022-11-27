package com.example.sugo_mvc.ui.adddealitem

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sugo_mvc.R

class AddrecycleAdapter(private val items : ArrayList<Uri>) :
    RecyclerView.Adapter<AddrecycleAdapter.ViewHolder>() {
    private lateinit var itemClickListener : OnItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView =LayoutInflater.from(parent.context).inflate(R.layout.addsugorecycleitem,parent,false)
        return ViewHolder(inflatedView)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            items[position].let { items ->
                with(holder.itemView){
                    Glide.with(context)
                        .load(items)
                        .override(500,500)
                        .into(holder.image)
                }
            }
    }
    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    class ViewHolder(v:View) : RecyclerView.ViewHolder(v){
        private var view:View = v
        var image = v.findViewById<ImageView>(R.id.addrecycleitem)
        fun bind(listener: View.OnClickListener ,item :String) {
                view.setOnClickListener (listener)
        }
    }
}