package com.sugo.app.feat.repository.repo.Chat

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sugo.app.feat.model.request.NoteBody
import com.sugo.app.feat.model.response.ChatRoom
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.model.response.NoteId
import com.sugo.app.feat.network.SugoRetrofit
import com.sugo.app.feat.repository.repo.detail.DetailDataSource
import com.sugo.app.feat.ui.common.ChatList
import com.sugo.app.feat.ui.common.MessageList
import com.sugo.app.feat.ui.common.MessageRoom
import retrofit2.Response

class ChatRemoteDataSource   (private val apiService: SugoRetrofit,
                              private val noteId:Long
) : PagingSource<Int, ChatRoom>() {
    override fun getRefreshKey(state: PagingState<Int, ChatRoom>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatRoom> {
        val position = params.key ?: 0
        return try {
            val products = apiService.getNoteContent(noteId ,position,10)
            val test= MessageRoom(products.body()!![1].toString())
           LoadResult.Page(
                data = ChatList(test),
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (ChatList(test).isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}