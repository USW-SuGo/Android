package com.sugo.app.feat.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sugo.app.databinding.ActivityContactPlaceBinding

class ContactPlaceActivity : AppCompatActivity() {
    private val binding by lazy { ActivityContactPlaceBinding.inflate(layoutInflater) }

    private lateinit var adapter:ContactPlaceAdapter //adapter객체 먼저 선언해주기!

    val mDatas=mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializelist()
        initRecyclerView()
    }
    fun initRecyclerView(){
        val adapter=ContactPlaceAdapter(mDatas) //어댑터 객체 만듦
        binding.contactrv.adapter=adapter //리사이클러뷰에 어댑터 연결
        binding.contactrv.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결
    }

    fun initializelist(){ //임의로 데이터 넣어서 만들어봄
        with(mDatas){
            add("정문")
            add("인문대")
            add("체대")
            add("미래혁신관")
            add("공대")
            add("IT")
            add("ACE교육관 (구 종합강의동)")
            add("학생회관")
            add("건강과학대학")
            add("중앙도서관")
            add("글로벌경상대")
        }
    }
}