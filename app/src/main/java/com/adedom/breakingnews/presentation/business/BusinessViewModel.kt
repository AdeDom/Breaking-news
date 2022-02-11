package com.adedom.breakingnews.presentation.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.base.BaseViewModel
import com.adedom.breakingnews.data.db.entities.BusinessEntity
import com.adedom.breakingnews.data.repository.Resource
import com.adedom.breakingnews.domain.usecase.GetBusinessUseCase
import kotlinx.coroutines.launch

class BusinessViewModel(
    private val getBusinessUseCase: GetBusinessUseCase,
) : BaseViewModel<BusinessViewState>(BusinessViewState()) {

    val getBusiness: LiveData<BusinessEntity> = getBusinessUseCase().asLiveData()

    fun callCategoryBusiness() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getBusinessUseCase.callCategoryBusiness()) {
                is Resource.Success -> {
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callCategoryBusinessNextPage(
        itemPosition: Int,
        businessSizeNow: Int? = getBusiness.value?.articles?.size,
        totalResults: Int? = getBusiness.value?.totalResults
    ) {
        if (businessSizeNow == itemPosition + 1 && businessSizeNow in (20..(totalResults ?: 20))) {
            launch {
                setState { copy(isLoading = true) }

                when (val resource = getBusinessUseCase.callCategoryBusinessNextPage()) {
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

    fun callCategoryBusinessSearch() {
        launch {
            setState { copy(isLoading = true) }

            val search = uiState.value.search
            when (val resource = getBusinessUseCase.callCategoryBusinessSearch(query = search)) {
                is Resource.Success -> {
                }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
