package com.sugo.app.feat.repository.repo.mainpage

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.network.SugoRetrofit

class ProductPagingDataSource(
    private val apiService : SugoRetrofit
) : PagingSource<Int,DealProduct>(){
    override fun getRefreshKey(state: PagingState<Int, DealProduct>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DealProduct> {
        val position = params.key ?:0
        return try {
            val Products = apiService.getMainPage(position,10,"")
            LoadResult.Page(
                data = Products,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (Products.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}