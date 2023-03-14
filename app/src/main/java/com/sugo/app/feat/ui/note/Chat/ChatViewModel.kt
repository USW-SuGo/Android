package com.sugo.app.feat.ui.note.Chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.request.Chat
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.repository.repo.Chat.ChatRepository
import com.sugo.app.feat.repository.repo.detail.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChatViewModel  (private val chatRepository: ChatRepository,private val detailRepository: DetailRepository) : ViewModel() {
    private val _dealPrduct2 = MutableLiveData<DealProduct>()
    val dealProduct2: LiveData<DealProduct> = _dealPrduct2
    private val _ChatContent = MutableLiveData<List<Long>>()
    val chatContent: LiveData<List<Long>> = _ChatContent
    fun detailProduct(productPostId: Long) = viewModelScope.launch {
        val response = detailRepository.detailProduct(productPostId)
        if(response.isSuccessful) _dealPrduct2.value = response.body()
    }
    fun getChatRoom(noteId:Long): Flow<PagingData<ChatRoom>> {
        return chatRepository.getNoteRoom(noteId)
    }
    fun getTest(a:Long,b:Long):List<Long>{
        val c = mutableListOf<Long>()
        _ChatContent.value= listOf(a,b)
        return c
    }
    fun sendChat(chat: Chat) = viewModelScope.launch {
        detailRepository.sendChat(chat)
    }

}