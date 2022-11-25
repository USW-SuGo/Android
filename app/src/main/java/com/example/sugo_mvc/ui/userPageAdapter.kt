package com.example.sugo_mvc.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.SuccessCheckDto
import com.example.sugo_mvc.databinding.DealrvitemBinding
import com.example.sugo_mvc.databinding.MypagervitemBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class userPageAdapter(val items: MutableList<DealMainItem>) : RecyclerView.Adapter<userPageAdapter.ViewHolder>() {

    companion object {
        const val ITEM = 1
        const val LOADING = 0
    }
    class ViewHolder(binding: MypagervitemBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: MypagervitemBinding

        fun bind(dealMainItem: DealMainItem){
            val tesl:List<String>
            val accessToken = App.prefs.AccessToken!!.replace("AccessToken=","")

            tesl=dealMainItem.imageLink.replace("[","").replace("]","").split(",")
            binding.dealrvid.text=dealMainItem.id.toString()
            binding.dealrvtitle.text = dealMainItem.title
            binding.dealrvprice.text = dealMainItem.price.toString()
            binding.dealrvplace.text = dealMainItem.contactPlace
            binding.dealNickname.text=dealMainItem.nickname
            binding.dealCategory.text=dealMainItem.category
            binding.dealDatetime.text= dealMainItem.updatedAt.toString()
            Glide.with(itemView).load(tesl[0]).into(binding.dealimageLnk)
            binding.safeBtn.setOnClickListener{
                RetrofitBuilder.service.upPost(accessToken,dealMainItem.id).enqueue(object :
                    Callback<SuccessCheckDto> {
                    override fun onResponse(
                        call: Call<SuccessCheckDto>,
                        response: Response<SuccessCheckDto>
                    ) {
                        Log.d("success",response.body().toString())
                    }

                    override fun onFailure(call: Call<SuccessCheckDto>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
            binding.refactorBtn.setOnClickListener {

            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userPageAdapter.ViewHolder {
        val binding = MypagervitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface ItemClick{ //인터페이스
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: userPageAdapter.ViewHolder, position: Int) {
        holder.bind(items!![position])
        val layoutParams = holder.itemView.layoutParams
        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView?.context, DealDetailActivity()::class.java)
            intent.putExtra("id",   holder.binding.dealrvid.text)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }

    }
    override fun getItemCount(): Int {
        return items.size
    }



}