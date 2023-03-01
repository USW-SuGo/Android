package com.sugo.app.feat.repository.repo.detail

import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.NoteId
import retrofit2.Response

class DetailRepository(
    private val detailRemoteDataSource:DetailRemoteDataSource
) {
    suspend fun detailProduct(productPostId: Long):Response<DealProduct>{
        return detailRemoteDataSource.detailProduct(productPostId)
    }
    suspend fun makeNote(noteBody: NoteBody):Response<NoteId>{
        return detailRemoteDataSource.makeNote(noteBody)
    }
}