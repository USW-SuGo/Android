package com.sugo.app.feat.repository.repo.detail

import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.ProductPostId
import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.Like
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.model.response.NoteId
import com.sugo.app.feat.model.response.NoteRoom
import com.sugo.app.feat.network.SugoRetrofit
import retrofit2.Response

class DetailRemoteDataSource(private val apiService:SugoRetrofit):DetailDataSource {
    override suspend fun detailProduct(productPostId: Long): Response<DealProduct> {
        return apiService.detailProduct(productPostId)
    }

    override suspend fun makeNote(noteBody: NoteBody): Response<NoteId> {
       return apiService.makeNote(noteBody)
    }

    override suspend fun like(productPostId: Long): Response<Like> {
        return apiService.like(ProductPostId( productPostId))
    }




}