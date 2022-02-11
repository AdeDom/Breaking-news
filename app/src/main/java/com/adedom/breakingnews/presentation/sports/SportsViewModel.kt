package com.adedom.breakingnews.presentation.sports

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.base.BaseViewModel
import com.adedom.breakingnews.data.db.entities.SportsEntity
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.usecase.GetSportsUseCase
import kotlinx.coroutines.launch

class SportsViewModel(
    private val getSportsUseCase: GetSportsUseCase,
) : BaseViewModel<SportsUiState>(SportsUiState()) {

    val getSports: LiveData<SportsEntity> = getSportsUseCase().asLiveData()

    fun callCategorySports() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getSportsUseCase.callCategorySports()) {
                is Resource.Success -> {
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callCategorySportsNextPage(
        itemPosition: Int,
        sportsSizeNow: Int? = getSports.value?.articles?.size,
        totalResults: Int? = getSports.value?.totalResults
    ) {
        if (sportsSizeNow == itemPosition + 1 && sportsSizeNow in (20..(totalResults ?: 20))) {
            launch {
                setState { copy(isLoading = true) }

                when (val resource = getSportsUseCase.callCategorySportsNextPage()) {
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

    fun callCategorySportsSearch() {
        launch {
            setState { copy(isLoading = true) }

            val search = uiState.value.search
            when (val resource = getSportsUseCase.callCategorySportsSearch(query = search)) {
                is Resource.Success -> {
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
