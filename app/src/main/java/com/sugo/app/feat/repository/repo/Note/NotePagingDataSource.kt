package com.sugo.app.feat.repository.repo.Note

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.model.response.NoteRoom
import com.sugo.app.feat.network.SugoRetrofit

class NotePagingDataSource(
    private val apiService : SugoRetrofit,
) : PagingSource<Int, NoteContent>(){
    override fun getRefreshKey(state: PagingState<Int, NoteContent>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoteContent> {
        val position = params.key ?:0
        return try {
            val Products = apiService.getNoteRoom(position,10)
            val result = Products.body()!!.noteContent
            LoadResult.Page(
                data = result,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (result.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}