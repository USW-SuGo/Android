package com.sugo.app.feat.repository

import com.sugo.app.feat.model.DealProduct

interface DealProductDataSource {
    suspend fun getCategories(): List<DealProduct>
}