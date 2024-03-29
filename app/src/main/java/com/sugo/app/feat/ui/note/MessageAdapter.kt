package com.sugo.app.feat.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sugo.app.databinding.ItemNoteBinding
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.ui.note.Chat.ChatViewModel

class MessageAdapter(
    private val viewModel: MessageViewModel, private val chatViewModel: ChatViewModel
) : PagingDataAdapter<NoteContent, MessageAdapter.PagingViewHolder>(
    NoteRoomDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PagingViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(noteContent: NoteContent) {
            binding.viewModel = viewModel
            binding.noteContent = noteContent
            binding.executePendingBindings()

        }
    }

}

class NoteRoomDiffCallback : DiffUtil.ItemCallback<NoteContent>() {
    override fun areItemsTheSame(oldItem: NoteContent, newItem: NoteContent): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: NoteContent, newItem: NoteContent): Boolean {
        return oldItem == newItem
    }

}