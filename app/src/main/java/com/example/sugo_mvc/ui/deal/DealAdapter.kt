package com.example.sugo_mvc.ui.deal

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sugo_mvc.databinding.DealrvitemBinding
import com.example.sugo_mvc.ui.DealDetailActivity

class DealAdapter(val items: MutableList<DealData>) : RecyclerView.Adapter<DealAdapter.ViewHolder>() {

    companion object {
        const val ITEM = 1
        const val LOADING = 0
    }
    class ViewHolder(binding: DealrvitemBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: DealrvitemBinding

        fun bind(dealData: DealData){

            binding.dealrvtitle.text = dealData.title
            binding.dealrvprice.text = dealData.price
            binding.dealrvplace.text = dealData.place
//            binding.dealpicture.setImageIcon()=memoData.pictureUrl
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
            val intent = Intent(holder.itemView?.context, DealDetailActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }




}