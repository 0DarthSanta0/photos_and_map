package com.projects.photos_and_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.photos_and_map.data.data_store.DataStoreManagerImpl
import com.projects.photos_and_map.data.repositories.login.LoginRepositoryImpl
import com.projects.photos_and_map.domain.use_cases.IsAuthorizedCheckUseCase

class MainActivityViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val loginRepository = LoginRepositoryImpl(DataStoreManagerImpl)
        return MainActivityViewModel(
            isAuthorizedCheckUseCase = IsAuthorizedCheckUseCase(loginRepository = loginRepository),
        ) as T
    }
}