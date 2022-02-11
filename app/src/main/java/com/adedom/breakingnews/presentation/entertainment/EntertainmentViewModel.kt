package com.adedom.breakingnews.presentation.entertainment

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.base.BaseViewModel
import com.adedom.breakingnews.data.db.entities.EntertainmentEntity
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.usecase.GetEntertainmentUseCase
import kotlinx.coroutines.launch

class EntertainmentViewModel(
    private val getEntertainmentUseCase: GetEntertainmentUseCase,
) : BaseViewModel<EntertainmentViewState>(EntertainmentViewState()) {

    val getEntertainment: LiveData<EntertainmentEntity> = getEntertainmentUseCase().asLiveData()

    fun callCategoryEntertainment() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getEntertainmentUseCase.callCategoryEntertainment()) {
                is Resource.Success -> {
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callCategoryEntertainmentNextPage(
        itemPosition: Int,
        entertainmentSizeNow: Int? = getEntertainment.value?.articles?.size,
        totalResults: Int? = getEntertainment.value?.totalResults
    ) {
        if (entertainmentSizeNow == itemPosition + 1 && entertainmentSizeNow in (20..(totalResults ?: 20))) {
            launch {
                setState { copy(isLoading = true) }

                when (val resource = getEntertainmentUseCase.callCategoryEntertainmentNextPage()) {
                    is Resource.Success -> {
                    }
                    is Resource.Error -> setError(resource.throwable)
                }

                setState { copy(isLoading = false) }
            }
        }
    }

    fun setStateSearch(search: String) {
        setState { copy(search = search) }
    }

    fun callCategoryEntertainmentSearch() {
        launch {
            setState { copy(isLoading = true) }

            val search = uiState.value.search
            when (val resource = getEntertainmentUseCase.callCategoryEntertainmentSearch(query = search)) {
                is Resource.Success -> {
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
