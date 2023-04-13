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

class ChatAdapter(
    private val viewModel: ChatViewModel,
) : PagingDataAdapter<ChatRoom, RecyclerView.ViewHolder>(
    ChatRoomDiffCallback()
) {
    companion object {
        const val VIEW_TYPE_MY_CHAT = 1
        const val VIEW_TYPE_OTHER_CHAT = 2
        const val VIEW_TYPE_MY_PICTURE = 3
        const val VIEW_TYPE_OTHER_PICTURE = 4
    }

    override fun getItemViewType(position: Int): Int {
        val request = getItem(itemCount - position - 1)?.requestUserId?.let {
            chatLong(it).replace(
                "{requestUserId=",
                ""
            ).toInt()
        }
        val sender = getItem(itemCount - position - 1)?.senderId?.let { chatLong(it).toInt() }
        val image = getItem(itemCount - position - 1)?.imageLink
        return if (request != sender) {
            if (image!!.length>5) {
                Log.d("1","1")
                VIEW_TYPE_OTHER_PICTURE
            } else {
                Log.d("1","2")
                VIEW_TYPE_OTHER_CHAT
            }
        } else {
            if (image!!.length>5) {
                Log.d("1","3")
                VIEW_TYPE_MY_PICTURE
            } else {
                Log.d("1","4")
                VIEW_TYPE_MY_CHAT
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MY_CHAT -> {
                val binding = ItemChattingUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MyChatViewHolder(binding)
            }
            VIEW_TYPE_OTHER_CHAT -> {
                val binding = ItemChattingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                OtherChatViewHolder(binding)
            }
            VIEW_TYPE_MY_PICTURE -> {
                val binding = ItemMyIvBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MyChatImageViewHolder(binding)
            }
            else -> {
                val binding = ItemOtherIvBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                OtherChatImageViewHolder(binding)
            }
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


