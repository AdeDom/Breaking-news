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

    fun callGeneral() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getGeneralUseCase.callBreakingNews()) {
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

}
