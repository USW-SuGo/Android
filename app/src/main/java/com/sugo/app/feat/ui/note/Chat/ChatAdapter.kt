package com.sugo.app.feat.ui.note.Chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemChattingBinding
import com.sugo.app.databinding.ItemChattingUserBinding
import com.sugo.app.databinding.ItemMyIvBinding
import com.sugo.app.databinding.ItemOtherIvBinding
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.ui.common.chatLong
import com.sugo.app.feat.ui.common.loadImage

class ChatAdapter(private val viewModel: ChatViewModel) :
    PagingDataAdapter<ChatRoom, RecyclerView.ViewHolder>(ChatRoomDiffCallback()) {

    companion object {
        const val VIEW_TYPE_MY_CHAT = 1
        const val VIEW_TYPE_OTHER_CHAT = 2
        const val VIEW_TYPE_MY_PICTURE = 3
        const val VIEW_TYPE_OTHER_PICTURE = 4
    }

    override fun getItemViewType(position: Int): Int {
        val chatRoom = getItem(itemCount - position - 1) ?: return VIEW_TYPE_MY_CHAT
        val request = chatLong(chatRoom.requestUserId)?.replace("{requestUserId=", "")?.toInt()
        val sender = chatLong(chatRoom.senderId)?.toInt()
        val image = chatRoom.imageLink

        return when {
            request != sender && image?.length ?: 0 > 5 -> VIEW_TYPE_OTHER_PICTURE
            request != sender && image?.length ?: 0 <= 5 -> VIEW_TYPE_OTHER_CHAT
            request == sender && image?.length ?: 0 > 5 -> VIEW_TYPE_MY_PICTURE
            else -> VIEW_TYPE_MY_CHAT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MY_CHAT -> {
                MyChatViewHolder(ItemChattingUserBinding.inflate(layoutInflater, parent, false))
            }
            VIEW_TYPE_OTHER_CHAT -> {
                OtherChatViewHolder(ItemChattingBinding.inflate(layoutInflater, parent, false))
            }
            VIEW_TYPE_MY_PICTURE -> {
                MyChatImageViewHolder(ItemMyIvBinding.inflate(layoutInflater, parent, false))
            }
            VIEW_TYPE_OTHER_PICTURE -> {
                OtherChatImageViewHolder(ItemOtherIvBinding.inflate(layoutInflater, parent, false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(itemCount - position - 1)?.let {
            when (holder) {
                is MyChatViewHolder -> holder.bind(it)
                is OtherChatViewHolder -> holder.bind(it)
                is MyChatImageViewHolder -> holder.bind(it)
                is OtherChatImageViewHolder -> holder.bind(it)
            }
        }
    }

    inner class MyChatViewHolder(private val binding: ItemChattingUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: ChatRoom) {
            binding.chatroom = chatRoom
            binding.executePendingBindings()
        }
    }

    inner class OtherChatViewHolder(private val binding: ItemChattingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: ChatRoom) {
            binding.chatroom = chatRoom
            binding.executePendingBindings()
        }
    }

    inner class MyChatImageViewHolder(private val binding: ItemMyIvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: ChatRoom) {
            loadImage(binding.ivMy, chatRoom.imageLink)
            binding.executePendingBindings()
        }
    }

    inner class OtherChatImageViewHolder(private val binding: ItemOtherIvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: ChatRoom) {
            loadImage(binding.ivOther, chatRoom.imageLink)
            binding.executePendingBindings()
        }
    }
}
class ChatRoomDiffCallback : DiffUtil.ItemCallback<ChatRoom>() {
    override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
        return oldItem.noteContentId == newItem.noteContentId
    }

    override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
        return oldItem == newItem
    }

}


