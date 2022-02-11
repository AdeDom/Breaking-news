package com.adedom.breakingnews.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<S : Any>(initialUiState: S) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        error.printStackTrace()
        setError(error)
    }

    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + exceptionHandler

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }

    /**
     * Given a currentState [reducer] and some action [setState] that the user took, produce a new [uiState].
     * This will give us clear and predictable state management, that ensures each state is associated
     * with some specific user event or action.
     * This [reducer] is responsible for handling any Action, and using that to create a new [uiState].
     */
    protected fun setState(reducer: S.() -> S) {
        _uiState.update {
            uiState.value.reducer()
        }
    }

    protected fun setError(error: Throwable) {
        _error.value = error
    }

}
