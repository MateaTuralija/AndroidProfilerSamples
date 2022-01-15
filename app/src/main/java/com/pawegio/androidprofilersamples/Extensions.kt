package com.pawegio.androidprofilersamples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pawegio.androidprofilersamples.model.Result
import kotlinx.coroutines.CoroutineScope

suspend fun <T> CoroutineScope.safeResponse(func: suspend CoroutineScope.() -> T): Result<T> {
    return try {
        Result.Success(func.invoke(this))
    } catch (e: Exception) {
        Result.Error(e)
    }
}

fun <T> MutableLiveData<T>.toImmutableLivedata(): LiveData<T> = this
