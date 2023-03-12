package com.sugo.app.feat.repository.repo.Note

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.network.SugoRetrofit
import kotlinx.coroutines.flow.Flow

class NotePagingRepositoryImpl (private val apiService: SugoRetrofit): NoteDataSource {

    override fun getNoteRoom(): Flow<PagingData<NoteContent>> =
    Pager(PagingConfig(10)){
        NotePagingDataSource(apiService)
    }.flow

}