package com.sugo.app.feat.ui.mypage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.sugo.app.R
import com.sugo.app.databinding.FragmentMypageBinding
import com.sugo.app.feat.App
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.ui.common.EventObserver
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.login.LoginActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyPageFragment : Fragment(),BottomSheetListner{
    private val viewModel: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentMypageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (App.loginform.value == false) {
            binding.ivMypageEdit.setOnClickListener{openSetting()}
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        } else {
            MyPageTest()
            binding.ivMypageEdit.setOnClickListener{openSetting()}

        }
    }

    private fun MyPageTest() {
        setUser()

        val pagingAdapter = initAdapter()
        productSubmitData(pagingAdapter, viewModel.getMyPageProduct())
        //이부분을  VM 으로 옮기기 뷰엣허 클릭시 센변경을 하는방법을 모르곘네  셀렉터인가 아닌가?
        binding.tvUserWrite.setOnClickListener {
            productSubmitData(pagingAdapter, viewModel.getMyPageProduct())
            binding.tvUserWrite.setTextColor(Color.parseColor("#000000"))
            binding.tvLikeWrite.setTextColor(Color.parseColor("#419e3a"))
        }
        binding.tvLikeWrite.setOnClickListener {
            productSubmitData(pagingAdapter, viewModel.getLikeProduct())
            binding.tvUserWrite.setTextColor(Color.parseColor("#419e3a"))
            binding.tvLikeWrite.setTextColor(Color.parseColor("#000000"))
        }
        openDailog()
        setNavigation()

    }

    private fun openDailog() {
        viewModel.openDealEvent1.observe(viewLifecycleOwner, EventObserver {
            val bottomSheetFragment = BottomSheetDialog(requireContext(), it)
            bottomSheetFragment.setCheckDialogListener2(this@MyPageFragment)
            bottomSheetFragment.show(parentFragmentManager, "childFragmentManager")
        })
    }

    private fun initAdapter(): MyPageAdapter {
        val pagingAdapter = MyPageAdapter(viewModel)
        binding.rvMypage.adapter = pagingAdapter
        return pagingAdapter
    }

    private fun setUser() {
        viewModel.myPage.observe(viewLifecycleOwner) {
            binding.mypage = it

        }
    }

    private fun setNavigation() {
        viewModel.openDealEvent.observe(viewLifecycleOwner, EventObserver {
            Log.d("productPostId", it.toString())
            openDealDetail(it)
        })
    }

    private fun openDealDetail(productPostId: Long) {
        findNavController().navigate(
            R.id.action_mypage_to_dealDetail, bundleOf(
                "productPostId" to productPostId
            )
        )
    }
    private fun openSetting() {
        findNavController().navigate(
            R.id.action_navigation_mypage_to_settingFragment, bundleOf(

            )
        )
    }

    private fun productSubmitData(pagingAdapter: MyPageAdapter, getData: Flow<PagingData<DealProduct>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { getData.collectLatest { pagingData -> pagingAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

    override fun onBottomSheetResult() {
        val pagingAdapter = initAdapter()
        productSubmitData(pagingAdapter, viewModel.getMyPageProduct())
    }

    /**
     * MyPageFragment 에서 CheckDialog 열고 클릭이벤트 상속받기
     * ViewModel을통하여 BottomSheetDialog 선택값을 라이브데이터에 저장
     * 다이얼로그를 사실상 2번 호출한다.
     * **/
}