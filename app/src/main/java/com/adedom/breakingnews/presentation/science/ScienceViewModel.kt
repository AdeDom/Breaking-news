package com.adedom.breakingnews.presentation.science

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.base.BaseViewModel
import com.adedom.breakingnews.data.db.entities.ScienceEntity
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.usecase.GetScienceUseCase
import kotlinx.coroutines.launch

class ScienceViewModel(
    private val getScienceUseCase: GetScienceUseCase,
) : BaseViewModel<ScienceViewState>(ScienceViewState()) {

    val getScience: LiveData<ScienceEntity> = getScienceUseCase().asLiveData()

    fun callCategoryScience() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getScienceUseCase.callCategoryScience()) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callCategoryScienceNextPage(
        itemPosition: Int,
        scienceSizeNow: Int? = getScience.value?.articles?.size,
        totalResults: Int? = getScience.value?.totalResults
    ) {
        if (scienceSizeNow == itemPosition + 1 && scienceSizeNow in (20..(totalResults ?: 20))) {
            launch {
                setState { copy(isLoading = true) }

                when (val resource = getScienceUseCase.callCategoryScienceNextPage()) {
                    is Resource.Error -> setError(resource.throwable)
                }

                setState { copy(isLoading = false) }
            }
        }
    }

    fun setStateSearch(search: String) {
        setState { copy(search = search) }
    }

    fun callCategoryScienceSearch() {
        launch {
            setState { copy(isLoading = true) }

            val search = state.value?.search ?: return@launch
            when (val resource = getScienceUseCase.callCategoryScienceSearch(query = search)) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
