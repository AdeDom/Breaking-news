package com.adedom.breakingnews.presentation.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.base.BaseViewModel
import com.adedom.breakingnews.data.db.entities.HealthEntity
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.usecase.GetHealthUseCase
import kotlinx.coroutines.launch

class HealthViewModel(
    private val getHealthUseCase: GetHealthUseCase,
) : BaseViewModel<HealthViewState>(HealthViewState()) {

    val getHealth: LiveData<HealthEntity> = getHealthUseCase().asLiveData()

    fun callCategoryHealth() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getHealthUseCase.callCategoryHealth()) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callCategoryHealthNextPage(
        itemPosition: Int,
        healthSizeNow: Int? = getHealth.value?.articles?.size
    ) {
        if (healthSizeNow == itemPosition + 1 && healthSizeNow in (20..99)) {
            launch {
                setState { copy(isLoading = true) }

                when (val resource = getHealthUseCase.callCategoryHealthNextPage()) {
                    is Resource.Error -> setError(resource.throwable)
                }

                setState { copy(isLoading = false) }
            }
        }
    }

    fun setStateSearch(search: String) {
        setState { copy(search = search) }
    }

    fun callCategoryHealthSearch() {
        launch {
            setState { copy(isLoading = true) }

            val search = state.value?.search ?: return@launch
            when (val resource = getHealthUseCase.callCategoryHealthSearch(query = search)) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
