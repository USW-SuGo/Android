package com.sugo.app.feat.repository.repo.Note

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingDataSource
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepository
import com.sugo.app.feat.repository.repo.mainpage.SearchPagingDataSource
import com.sugo.app.feat.repository.repo.mypage.MyLikePagingDataSource
import com.sugo.app.feat.repository.repo.mypage.MyPagePagingDataSource
import kotlinx.coroutines.flow.Flow

class NotePagingRepositoryImpl (private val apiService: SugoRetrofit): NoteDataSource {

    override fun getNoteRoom(): Flow<PagingData<NoteContent>> =
    Pager(PagingConfig(10)){
        NotePagingDataSource(apiService)
    }.flow

}