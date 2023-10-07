package com.mutsuddi_s.starwars.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mutsuddi_s.starwars.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

open class SafeApiCall {



  suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Error(throwable.response()?.errorBody().toString())
                    }
                    else -> {
                        Resource.Error(throwable.localizedMessage)
                    }
                }
            }
        }
    }
}