package com.example.sugo_mvc.ui.deal

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.databinding.DealrvitemBinding
import com.example.sugo_mvc.ui.DealDetailActivity


class DealAdapter(var items: MutableList<DealMainItem>) :
    RecyclerView.Adapter<DealAdapter.ViewHolder>(),Filterable {
    var filterItems= mutableListOf<DealMainItem>()
    var itemFilter= ItemFilter()

    companion object {

        const val ITEM = 1
        const val LOADING = 0
    }
    override fun getFilter(): Filter {
        return itemFilter
    }
    init {
        filterItems=items
//        filterItems.addAll(items)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DealrvitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    class ViewHolder(binding: DealrvitemBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: DealrvitemBinding

        fun bind(dealMainItem: DealMainItem){
            val tesl:List<String>
            tesl=dealMainItem.imageLink.replace("[","").replace("]","").split(",")
            binding.dealrvid.text=dealMainItem.id.toString()
            binding.dealrvtitle.text = dealMainItem.title
            binding.dealrvprice.text = dealMainItem.price.toString()
            binding.dealrvplace.text = dealMainItem.contactPlace
            binding.dealNickname.text=dealMainItem.nickname
            binding.dealCategory.text=dealMainItem.category
            binding.dealDatetime.text= dealMainItem.updatedAt.toString()
            Glide.with(itemView).load(tesl[0]).into(binding.dealimageLnk)
        }
        init {
            this.binding = binding
            //item Click Listener

            binding.root.setOnClickListener({
                val pos = adapterPosition
                Log.d("click", pos.toString() + " : click!")
            })
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (filterItems[position] != null) {
            ITEM
        } else {
            LOADING
        }
    }




    override fun getItemCount(): Int {
        return filterItems.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterItems[position])
        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView.context, DealDetailActivity()::class.java)
            intent.putExtra("id",   holder.binding.dealrvid.text)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

    }
    fun initFilteredPersons() {
        filterItems.clear()
        filterItems.addAll(items)
    }
    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            Log.d(TAG, "charSequence : $charSequence")
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.isEmpty()) {
                filterItems = items
//                return results
            } else {
                val resultList= mutableListOf<DealMainItem>()
                for (item in items) {
                    Log.d("search title",item.title)
                    if (item.title.contains(filterString)|| item.category.contains(filterString)) {
                        resultList.add(item)
                        Log.d("searchfilter",resultList.toString())
                    }
                }
                filterItems=resultList
            }
            val results = FilterResults()
            results.values=filterItems
            Log.d("searchfilter2",results.values.toString())
//            notifyDataSetChanged()
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults) {

            filterItems=p1.values as MutableList<DealMainItem>
            Log.d("searchfilter3",filterItems.toString())
            notifyDataSetChanged()
        }

    }
}