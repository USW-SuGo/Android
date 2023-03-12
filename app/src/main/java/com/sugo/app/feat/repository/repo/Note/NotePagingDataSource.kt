package com.sugo.app.feat.repository.repo.Note

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.ui.common.MessageList
import com.sugo.app.feat.ui.common.MessageRoom

class NotePagingDataSource(
    private val apiService: SugoRetrofit,
) : PagingSource<Int, NoteContent>() {
    override fun getRefreshKey(state: PagingState<Int, NoteContent>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }


    //Any로하면 너무 하드코딩인데 여기 리팩토링 필요
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoteContent> {
        val position = params.key ?: 0
        return try {
            val Products = apiService.getNoteRoom(position, 10)
            val a = MessageRoom(Products.body()!![1].toString())
            LoadResult.Page(
                data = MessageList(a),
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (MessageList(a).isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}