package com.sugo.app.feat.repository.repo.Note

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.network.SugoRetrofit

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
            val a:List<String> = Products.body()!![1].toString().replace("{","").replace("[","").replace("]","").split("},")
            val test2 = mutableListOf<NoteContent>()
            for (i in 0..a.size-1){
                val b = a[i].split(",")
                test2.add(NoteContent(b[0].replace("imageLink=","").replace(" ",""),b[1],b[2] ,b[3] ,b[4],b[5].replace("opponentUserNickname=",""),
                    b[6].replace("recentContent=",""),b[7].replace("requestUserUnreadCount=",""),b[8].replace("recentChattingDate=","")))
            }
            LoadResult.Page(
                data = test2,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (test2.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}