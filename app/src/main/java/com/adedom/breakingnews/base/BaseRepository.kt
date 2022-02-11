package com.adedom.breakingnews.base

import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    suspend fun <T : Any> callApi(
        service: suspend CoroutineScope.() -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(service())
            } catch (throwable: Throwable) {
                Resource.Error(throwable)
            }
        }
    }

}
