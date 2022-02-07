package com.adedom.breakingnews.presentation.technology

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.base.BaseViewModel
import com.adedom.breakingnews.data.db.entities.TechnologyEntity
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.usecase.GetTechnologyUseCase
import kotlinx.coroutines.launch

class TechnologyViewModel(
    private val getTechnologyUseCase: GetTechnologyUseCase,
) : BaseViewModel<TechnologyViewState>(TechnologyViewState()) {

    val getTechnology: LiveData<TechnologyEntity> = getTechnologyUseCase().asLiveData()

    fun callCategoryTechnology() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getTechnologyUseCase.callCategoryTechnology()) {
                is Resource.Success -> {
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callCategoryTechnologyNextPage(
        itemPosition: Int,
        technologySizeNow: Int? = getTechnology.value?.articles?.size,
        totalResults: Int? = getTechnology.value?.totalResults
    ) {
        if (technologySizeNow == itemPosition + 1 && technologySizeNow in (20..(totalResults ?: 20))) {
            launch {
                setState { copy(isLoading = true) }

                when (val resource = getTechnologyUseCase.callCategoryTechnologyNextPage()) {
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

    fun callCategoryTechnologySearch() {
        launch {
            setState { copy(isLoading = true) }

            val search = state.value?.search ?: return@launch
            when (val resource = getTechnologyUseCase.callCategoryTechnologySearch(query = search)) {
                is Resource.Success -> {
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
