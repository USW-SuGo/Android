package com.sugo.app.feat.repository.repo.detail

import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.Like
import com.sugo.app.feat.model.response.NoteId
import retrofit2.Response

interface DetailDataSource {
   suspend fun detailProduct(productPostId:Long):Response<DealProduct>
   suspend fun makeNote(noteBody: NoteBody):Response<NoteId>

   suspend fun like(productPostId: Long):Response<Like>
}