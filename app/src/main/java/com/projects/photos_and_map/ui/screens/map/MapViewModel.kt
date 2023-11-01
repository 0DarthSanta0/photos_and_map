package com.projects.photos_and_map.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.michaelbull.result.onSuccess
import com.projects.photos_and_map.data.repositories.map.MapRepositoryImpl
import com.projects.photos_and_map.domain.use_cases.GetMarksUseCase
import com.projects.photos_and_map.models.Mark
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(
    private val getMarksUseCase: GetMarksUseCase
) : ViewModel() {
    private val _marks: MutableStateFlow<List<Mark>> = MutableStateFlow(listOf())
    val marks: StateFlow<List<Mark>> get() = _marks

    init {
        getPoints()
    }

    private fun getPoints() {
        viewModelScope.launch {
            getMarksUseCase().collect {getMarksRequest ->
                getMarksRequest.onSuccess { marks ->
                    _marks.value = marks
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val mapRepositoryImpl = MapRepositoryImpl()
                MapViewModel(
                    getMarksUseCase = GetMarksUseCase(mapRepositoryImpl)
                )
            }
        }
    }
}