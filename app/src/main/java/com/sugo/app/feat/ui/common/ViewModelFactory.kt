package com.sugo.app.feat.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sugo.app.feat.ServiceLocator
import com.sugo.app.feat.repository.repo.Chat.ChatRepository
import com.sugo.app.feat.repository.repo.Note.NotePagingRepositoryImpl
import com.sugo.app.feat.repository.repo.detail.DetailRemoteDataSource
import com.sugo.app.feat.repository.repo.detail.DetailRepository
import com.sugo.app.feat.repository.repo.join.JoinRemoteDataSource
import com.sugo.app.feat.repository.repo.join.JoinRepository
import com.sugo.app.feat.repository.repo.login.LoginRemoteDataSource
import com.sugo.app.feat.repository.repo.login.LoginRepository
import com.sugo.app.feat.repository.repo.mainpage.ProductPagingRepositoryImpl
import com.sugo.app.feat.repository.repo.mypage.MyPageRemoteDataSource
import com.sugo.app.feat.repository.repo.mypage.MyPageRepository
import com.sugo.app.feat.repository.repo.user.UserRemoteDataSource
import com.sugo.app.feat.repository.repo.user.UserRepository
import com.sugo.app.feat.ui.note.MessageViewModel
import com.sugo.app.feat.ui.deal.ProductPagingViewModel
import com.sugo.app.feat.ui.deal.SearchPagingViewModel
import com.sugo.app.feat.ui.dealdetail.DetailViewModel
import com.sugo.app.feat.ui.join.inputUser.JoinViewModel
import com.sugo.app.feat.ui.login.LoginViewModel
import com.sugo.app.feat.ui.mypage.MyPageViewModel
import com.sugo.app.feat.ui.note.Chat.ChatViewModel
import com.sugo.app.feat.ui.upload.UploadViewModel
import com.sugo.app.feat.ui.user.UserViewModel


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductPagingViewModel::class.java) -> {
                val repository =
                    ProductPagingRepositoryImpl((ServiceLocator.provideApiClient()))
                ProductPagingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MyPageViewModel::class.java) -> {
                val repository =
                    ProductPagingRepositoryImpl((ServiceLocator.provideApiClient()))
                val repository2 =
                    MyPageRepository(MyPageRemoteDataSource(ServiceLocator.provideTokenApiClient()))
                MyPageViewModel(repository2,repository) as T
            }
            modelClass.isAssignableFrom(SearchPagingViewModel::class.java) -> {
                val repository =
                    ProductPagingRepositoryImpl((ServiceLocator.provideTokenApiClient()))
                SearchPagingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MessageViewModel::class.java) -> {
                val repository =
                    NotePagingRepositoryImpl((ServiceLocator.provideTokenApiClient()))
                MessageViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                val repository =
                    ChatRepository((ServiceLocator.provideTokenApiClient()))
                val repository2 =
                    DetailRepository(DetailRemoteDataSource(ServiceLocator.provideTokenApiClient()))
               ChatViewModel(repository,repository2) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                val repository =
                    LoginRepository(LoginRemoteDataSource(ServiceLocator.provideTokenApiClient()))
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(JoinViewModel::class.java) ->{
                val repository =
                    JoinRepository(JoinRemoteDataSource(ServiceLocator.provideApiClient()))
                JoinViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->{
                val repository =
                    DetailRepository(DetailRemoteDataSource(ServiceLocator.provideTokenApiClient()))
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java) ->{
                UploadViewModel() as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) ->{
                val repository =
                   UserRepository(UserRemoteDataSource(ServiceLocator.provideTokenApiClient()))
                    UserViewModel(repository) as T
            }

            else -> {
                throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
            }
        }
    }
}