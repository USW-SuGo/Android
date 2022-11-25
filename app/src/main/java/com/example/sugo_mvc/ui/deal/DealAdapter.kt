package com.example.sugo_mvc.ui.deal

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.databinding.DealrvitemBinding
import com.example.sugo_mvc.ui.DealDetailActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DealAdapter(val items: MutableList<DealMainItem>) :
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
            binding.root.setOnClickListener(View.OnClickListener {
                val pos = adapterPosition
                Log.d("click", pos.toString() + " : click!")
            })
        }
    }
    init {
        filterItems.addAll(items)
    }
    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val progressBar = itemView.findViewById<ProgressBar>(R.id.rv_loading_pb)!!
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) {
            ITEM
        } else {
            LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealAdapter.ViewHolder {
            val binding = DealrvitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
           return ViewHolder(binding)
    }

    interface ItemClick{ //인터페이스
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: DealAdapter.ViewHolder, position: Int) {
        holder.bind(items!![position])
        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView?.context, DealDetailActivity()::class.java)
            intent.putExtra("id",   holder.binding.dealrvid.text)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }


    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            Log.d(TAG, "charSequence : $charSequence")
            val results = FilterResults()
            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: MutableList<DealMainItem> = mutableListOf<DealMainItem>()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = items
                results.count = items.size
                return results
            } else {
                for (item in items) {
                    Log.d("search title",item.title.toString())
                    if (item.title.contains(filterString)) {
                        filteredList.add(item)
                        Log.d("searchfilter",filteredList.toString())
                    }
                }
            }
            Log.d("searchfilter",filteredList.toString())
            results.values = filteredList
            results.count = filteredList.size
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults) {
            filterItems.clear()
            filterItems.addAll(p1.values as MutableList<DealMainItem>)
            notifyDataSetChanged()
        }
//        @SuppressLint("NotifyDataSetChanged")

    }
}