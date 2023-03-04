package com.sugo.app.feat.ui.note


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.sugo.app.databinding.FragmentMessageBinding
import com.sugo.app.feat.model.DealProduct
import com.sugo.app.feat.model.response.NoteContent
import com.sugo.app.feat.ui.common.ViewModelFactory
import com.sugo.app.feat.ui.mypage.MyPageAdapter
import com.sugo.app.feat.ui.mypage.MyPageViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessageFragment : Fragment() {
    private val viewModel: MessageViewModel by viewModels { ViewModelFactory(requireContext()) }
    private val viewModel2: MyPageViewModel by viewModels { ViewModelFactory(requireContext()) }

    private lateinit var binding: FragmentMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagingAdapter = initAdapter()

    }
    private fun initAdapter(): MessageAdapter {

        val pagingAdapter = MessageAdapter(viewModel)
        binding.rvNote.adapter = pagingAdapter
        productSubmitData(pagingAdapter, viewModel.getNoteRoom())
        return pagingAdapter
    }
    private fun initAdapter2(): MyPageAdapter {
        val pagingAdapter = MyPageAdapter(viewModel2)
        binding.rvNote.adapter = pagingAdapter
        productSubmitData2(pagingAdapter, viewModel2.getMyPageProduct())
        return pagingAdapter
    }
    private fun productSubmitData2(pagingAdapter: MyPageAdapter, getData: Flow<PagingData<DealProduct>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { getData.collectLatest { pagingData -> pagingAdapter.submitData(pagingData)
                    Log.d("adapter",pagingData.toString())
                }
                }
            }
        }
    }

    private fun productSubmitData(pagingAdapter: MessageAdapter, getData: Flow<PagingData<NoteContent>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { getData.collectLatest {
                        pagingData -> pagingAdapter.submitData(pagingData)
                     Log.d("adapter",pagingData.toString())
                }
                }
            }
        }
    }
}
