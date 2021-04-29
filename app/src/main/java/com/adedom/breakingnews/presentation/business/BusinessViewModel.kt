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
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callCategoryBusinessNextPage(
        itemPosition: Int,
        businessSizeNow: Int? = getBusiness.value?.articles?.size
    ) {
        if (businessSizeNow == itemPosition + 1 && businessSizeNow in (20..99)) {
            launch {
                setState { copy(isLoading = true) }

                when (val resource = getBusinessUseCase.callCategoryBusinessNextPage()) {
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

            val search = state.value?.search ?: return@launch
            when (val resource = getBusinessUseCase.callCategoryBusinessSearch(query = search)) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
