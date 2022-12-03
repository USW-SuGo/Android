package com.example.sugo_mvc.ui.user

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sugo_mvc.data.DealMainItem
import com.example.sugo_mvc.data.ProductPostId
import com.example.sugo_mvc.data.SuccessCheckDto
import com.example.sugo_mvc.databinding.MypagervitemBinding
import com.example.sugo_mvc.retofit.RetrofitBuilder
import com.example.sugo_mvc.ui.adddealitem.AddItemActivity
import com.example.sugo_mvc.ui.deal.DealDetailActivity
import com.example.sugo_mvc.util.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Duration
import java.time.LocalDateTime


class userPageAdapter(val items: MutableList<DealMainItem>) :
    RecyclerView.Adapter<userPageAdapter.ViewHolder>() {
    companion object {
        const val ITEM = 1
        const val LOADING = 0
    }

    class ViewHolder(binding: MypagervitemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: MypagervitemBinding
        fun bind(dealMainItem: DealMainItem) {
            val tesl: List<String>
            val accessToken = App.prefs.AccessToken!!.replace("AccessToken=", "")

            tesl = dealMainItem.imageLink.replace("[", "").replace("]", "").split(",")
            var a=""
            val startDateTime = LocalDateTime.now()
            val endDateTime =dealMainItem.updatedAt
            val duration: Duration = Duration.between(startDateTime, endDateTime)
            Log.d("duration",duration.toDays().toString().replace("-",""))
            if(duration.toDays().toString().replace("-","")=="0")  a="오늘"
            if(duration.toDays().toString().replace("-","")=="1")  a="어제"
            if(duration.toDays().toString().replace("-","")!="1"&&
                duration.toDays().toString().replace("-","")!="0")  a=duration.toDays().toString().replace("-","")+"일 전"

            binding.dealrvid.text = dealMainItem.productPostId.toString()
            binding.dealrvtitle.text = dealMainItem.title
            binding.dealrvprice.text = dealMainItem.price.toString()+"원"
            binding.dealrvplace.text = dealMainItem.contactPlace+" | "+a+" | "+dealMainItem.category
            binding.dealNickname.text = dealMainItem.nickname
            Glide.with(itemView).load(tesl[0]).into(binding.dealimageLnk)
            binding.safeBtn.setOnClickListener() {
                RetrofitBuilder.service.upPost(accessToken, ProductPostId( dealMainItem.productPostId)).enqueue(object :
                    Callback<SuccessCheckDto> {
                    override fun onResponse(
                        call: Call<SuccessCheckDto>,
                        response: Response<SuccessCheckDto>
                    ) {
                        if (response.isSuccessful) {
                            dialog(binding.root.context).showDialog2()
                            Log.d("success", response.body().toString())
                        } else {
                            dialog(binding.root.context).showDialog()
                        }
                    }
                    override fun onFailure(call: Call<SuccessCheckDto>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })

            }
            binding.refactorBtn.setOnClickListener {
                val intent = Intent(binding.root.context, AddItemActivity::class.java)
                startActivity(binding.root.context, intent, null)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MypagervitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface ItemClick { //인터페이스
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items!![position])
        val layoutParams = holder.itemView.layoutParams
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView?.context, DealDetailActivity()::class.java)
            intent.putExtra("id", holder.binding.dealrvid.text)
            intent.putExtra("safebtn", holder.binding.safebbtn.text)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


}