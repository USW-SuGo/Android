package com.sugo.app.feat.ui.note

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.repository.repo.Note.NotePagingRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MessageViewModel (private val repoImpl: NotePagingRepositoryImpl) : ViewModel() {
    private val _itmes = MutableStateFlow<PagingData<DealProduct>>(PagingData.empty())
    val items = _itmes.asStateFlow()

    fun getNoteRoom(): Flow<PagingData<NoteContent>> {
        return repoImpl.getNoteRoom()
    }
}
