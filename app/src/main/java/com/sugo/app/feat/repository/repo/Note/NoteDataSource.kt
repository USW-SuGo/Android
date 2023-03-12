package com.sugo.app.feat.repository.repo.Note

import androidx.paging.PagingData
import com.sugo.app.feat.model.response.NoteContent
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
  fun getNoteRoom(): Flow<PagingData<NoteContent>>
}