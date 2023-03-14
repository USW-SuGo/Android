package com.sugo.app.feat.ui.note.Chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemChattingBinding
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.ui.common.chatLong

class ChatAdapter (
    private val viewModel: ChatViewModel,
) : PagingDataAdapter<ChatRoom, ChatAdapter.PagingViewHolder>(
   ChatRoomDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ItemChattingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(itemCount - position - 1)?.let { holder.bind(it) }
    }

    inner class PagingViewHolder(private val binding: ItemChattingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //
        fun bind(noteContent: ChatRoom) {
            val requestUserId =chatLong(noteContent.requestUserId).replace("{requestUserId=","").toInt()
            if (chatLong(noteContent.senderId!!).toInt()==requestUserId){
                binding.chatroom2= noteContent
            }
            else{
                binding.chatroom= noteContent
            }
            binding.executePendingBindings()
        }
    }

}
class ChatRoomDiffCallback : DiffUtil.ItemCallback<ChatRoom>() {
    override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
        return oldItem.noteContentId== newItem.noteContentId
    }
    override fun areContentsTheSame(oldItem:ChatRoom, newItem: ChatRoom): Boolean {
        return oldItem == newItem
    }

}