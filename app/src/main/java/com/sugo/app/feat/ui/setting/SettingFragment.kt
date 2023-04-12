package com.sugo.app.feat.ui.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sugo.app.databinding.FragmentSettingBinding
import com.sugo.app.feat.ui.common.User
import com.sugo.app.feat.ui.login.LoginActivity

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLogincheck.setOnClickListener {
            val nextIntent = Intent(activity, LoginActivity::class.java)
            startActivity(nextIntent)
        }
        binding.tvNotification.setOnClickListener {
            Log.d("Clicklogout", "test")
            User.logout()
        }
    }
}
