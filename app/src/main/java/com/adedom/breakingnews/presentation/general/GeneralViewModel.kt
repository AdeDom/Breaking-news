package com.adedom.breakingnews.presentation.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.base.BaseViewModel
import com.adedom.breakingnews.data.db.entities.GeneralEntity
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.usecase.GetGeneralUseCase
import kotlinx.coroutines.launch

class GeneralViewModel(
    private val getGeneralUseCase: GetGeneralUseCase,
) : BaseViewModel<GeneralViewState>(GeneralViewState()) {

    val getGeneral: LiveData<GeneralEntity> = getGeneralUseCase().asLiveData()

    fun callCategoryGeneral() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getGeneralUseCase.callCategoryGeneral()) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callCategoryGeneralNextPage(
        itemPosition: Int,
        generalSizeNow: Int? = getGeneral.value?.articles?.size,
        totalResults: Int? = getGeneral.value?.totalResults
    ) {
        if (generalSizeNow == itemPosition + 1 && generalSizeNow in (20..(totalResults ?: 20))) {
            launch {
                setState { copy(isLoading = true) }

                when (val resource = getGeneralUseCase.callCategoryGeneralNextPage()) {
                    is Resource.Error -> setError(resource.throwable)
                }

                setState { copy(isLoading = false) }
            }
        }
    }

    fun setStateSearch(search: String) {
        setState { copy(search = search) }
    }

    fun callCategoryGeneralSearch() {
        launch {
            setState { copy(isLoading = true) }

            val search = state.value?.search ?: return@launch
            when (val resource = getGeneralUseCase.callCategoryGeneralSearch(query = search)) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
