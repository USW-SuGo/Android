package com.example.sugo_mvc.ui.messageRoom

import android.content.Intent
import android.graphics.Color.rgb
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.data.NoteItem
import com.example.sugo_mvc.data.msgformat
import com.example.sugo_mvc.data.roomInfo
import com.example.sugo_mvc.databinding.DealmessagervitemBinding
import com.example.sugo_mvc.databinding.MessagecontentrvitemBinding

class MessageContentAdapter(val items: MutableList<NoteItem>) : RecyclerView.Adapter<MessageContentAdapter.ViewHolder>() {

    companion object {
        const val ITEM = 1
        const val LOADING = 0
    }
    class ViewHolder(binding: MessagecontentrvitemBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: MessagecontentrvitemBinding
        fun bind(dealMainItem: NoteItem) {
            binding.contentmsg.text = dealMainItem.message
            binding.contentmsgsender.text = dealMainItem.messageSenderId.toString()
            binding.contentmsgreceiver.text = dealMainItem.messageReceiverId.toString()
            binding.contentmsgcreateat.text = dealMainItem.messageCreatedAt.toString()
            if (dealMainItem.requestUserId == dealMainItem.messageSenderId){
                binding.contentwho.text = "보낸 쪽지"
            binding.contentwho.setTextColor(rgb(255, 0, 0))
        }else{
                binding.contentwho.text="받은 쪽지"
                binding.contentwho.setTextColor(rgb(0, 255, 0))
        }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageContentAdapter.ViewHolder {
        val binding = MessagecontentrvitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface ItemClick{ //인터페이스
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: MessageContentAdapter.ViewHolder, position: Int) {
        holder.bind(items!![position])

        holder.itemView.setOnClickListener{

//            val intent = Intent(holder.itemView?.context, MessageContentActivity()::class.java)
//            intent.putExtra("id",   holder.binding.roomId.text)
//            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }



}