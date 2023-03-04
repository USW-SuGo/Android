package com.sugo.app.feat.repository.repo.Note

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.model.response.NoteRoom
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NoteDataSource {

  fun getNoteRoom(): Flow<PagingData<NoteContent>>
}