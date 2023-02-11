package com.sugo.app.feat.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.sugo.app.feat.model.DealProduct


class DealProductDiffCallback : DiffUtil.ItemCallback<DealProduct>() {
    override fun areItemsTheSame(oldItem: DealProduct, newItem: DealProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DealProduct, newItem: DealProduct): Boolean {
        return oldItem == newItem
    }

}