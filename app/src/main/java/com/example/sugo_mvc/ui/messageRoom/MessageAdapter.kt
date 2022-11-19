package com.example.sugo_mvc.ui.messageRoom

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.roomAll
import com.example.sugo_mvc.data.roomInfo
import com.example.sugo_mvc.databinding.DealmessagervitemBinding
import com.example.sugo_mvc.databinding.DealrvitemBinding
import com.example.sugo_mvc.ui.DealDetailActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageAdapter(val items: MutableList<roomInfo>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    companion object {
        const val ITEM = 1
        const val LOADING = 0
    }
    class ViewHolder(binding: DealmessagervitemBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: DealmessagervitemBinding
        fun bind(dealMainItem: roomInfo){
            binding.roomId.text=dealMainItem.roomId.toString()
            binding.requestUserid.text=dealMainItem.requestUserId.toString()
//            binding.recentcontent.text=dealMainItem.recentContent.toString()
            binding.recentChattingtime.text=dealMainItem.recentChattingDate.toString()
            binding.messagesender.text=dealMainItem.opponentUserNickname.toString()
            binding.unreadcount.text=dealMainItem.requestUserUnreadCount.toString()
            binding.myname.text=dealMainItem.opponentUserId.toString()
            }


        init {
            this.binding = binding
            //item Click Listener
            binding.root.setOnClickListener(View.OnClickListener {
                val pos = adapterPosition
                Log.d("click", pos.toString() + " : click!")
            })

        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) {
            ITEM
        } else {
            LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
            val binding = DealmessagervitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
           return ViewHolder(binding)
    }

    interface ItemClick{ //인터페이스
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        holder.bind(items!![position])
        val layoutParams = holder.itemView.layoutParams

        layoutParams.height = 1000
        holder.itemView.requestLayout()
        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView?.context, DealDetailActivity()::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }



}