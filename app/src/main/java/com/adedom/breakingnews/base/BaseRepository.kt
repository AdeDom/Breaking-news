package com.adedom.breakingnews.base

import com.adedom.breakingnews.data.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Resource<T> =
        withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                if (throwable is HttpException && (throwable.code() == 401 || throwable.code() == 403)) {
                    Resource.Error(throwable, true)
                } else {
                    Resource.Error(throwable, false)
                }
            }
        }

}
