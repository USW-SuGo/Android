package com.sugo.app.feat.ui.upload

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sugo.app.R

class UploadAdapter(private val items : ArrayList<Uri>) :
    RecyclerView.Adapter<UploadAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
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
        holder.delete.setOnClickListener {
            removeItem(position)
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(position :Int){
        if(position>=0){
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(v:View) : RecyclerView.ViewHolder(v){
        private var view:View = v
        var image = v.findViewById<ImageView>(R.id.addrecycleitem)
        var delete=v.findViewById<ImageView>(R.id.deleteimage)

    }
}