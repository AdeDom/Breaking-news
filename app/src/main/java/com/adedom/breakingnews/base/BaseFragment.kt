package com.adedom.breakingnews.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.utils.toast
import kotlinx.coroutines.flow.StateFlow

abstract class BaseFragment : Fragment() {

    open fun setupViewModel() {}

    open fun setupAdapter() {}

    open fun setupView() {}

    open fun setupEvent() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAdapter()
        setupView()
        setupEvent()
    }

    protected inline fun <reified T> LiveData<T>.observe(crossinline onNext: (T) -> Unit) {
        observe(this@BaseFragment) { onNext(it) }
    }

    protected inline fun <reified T> StateFlow<T>.observe(crossinline onNext: (T) -> Unit) {
        asLiveData().observe(this@BaseFragment) {
            if (it != null) {
                onNext(it)
            }
        }
    }

    protected fun LiveData<Throwable>.observeError() {
        observe(this@BaseFragment) {
            it.printStackTrace()
            context.toast("BaseFragment : observeError ${it.message}", Toast.LENGTH_LONG)
        }
    }

}
