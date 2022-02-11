package com.adedom.breakingnews.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adedom.breakingnews.utils.toast
import kotlinx.coroutines.flow.StateFlow

abstract class BaseActivity : AppCompatActivity() {

    protected inline fun <reified T> LiveData<T>.observe(crossinline onNext: (T) -> Unit) {
        observe(this@BaseActivity) { onNext(it) }
    }

    protected inline fun <reified T> StateFlow<T>.observe(crossinline onNext: (T) -> Unit) {
        asLiveData().observe(this@BaseActivity) {
            if (it != null) {
                onNext(it)
            }
        }
    }

    protected fun LiveData<Throwable>.observeError() {
        observe(this@BaseActivity) {
            it.printStackTrace()
            toast("BaseActivity : observeError ${it.message}", Toast.LENGTH_LONG)
        }
    }

}
