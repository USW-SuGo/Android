package com.sugo.app.feat.ui.join.inputUser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sugo.app.R
import com.sugo.app.databinding.ActivityJoinBinding
import com.sugo.app.feat.ui.common.ViewModelFactory

class JoinActivity : AppCompatActivity() {
    private val viewModel: JoinViewModel by viewModels { ViewModelFactory(this) }
    private lateinit var binding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        binding.viewmodel=viewModel
//        IntroChange()
//        binding.btnNext.setOnClickListener {
//            val fragment1 = SelectMajorFragment()
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.main_container2, fragment1)
//                .commit()
//        }
    }
//    private fun IntroChange() {
//        viewModel.introid.observe( this) {
//            Log.d("tvIdintro", it)
//            binding.tvCheckIdIntro.text = it
//        }
//        viewModel.introEmail.observe( this) {
//            Log.d("tvEmailintro",it)
//            binding.tvEmailIntro.text = it
//        }
//        viewModel.introPwd.observe( this) {
//            Log.d("tvPwdintro",it)
//            binding.tvCheckPwdIntro.text = it
//        }
//    }
}
