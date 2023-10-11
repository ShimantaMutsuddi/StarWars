package com.mutsuddi_s.starwars.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mutsuddi_s.starwars.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * This Kotlin class, `SafeApiCall`, provides a mechanism for making safe asynchronous API calls. It abstracts common error handling logic that can occur during network requests, such as HTTP exceptions, and wraps the results in a `Resource` object to provide a standardized way of handling responses.
 *
 * @file SafeApiCall.kt
 * @package com.mutsuddi_s.starwars.data.remote
 */

open class SafeApiCall {

    /**
     * Executes a safe asynchronous API call and handles errors to return a `Resource` object containing the result.
     *
     * @param apiCall A suspend lambda function representing the network request.
     * @return A `Resource` object encapsulating the result of the API call.
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                // Attempt the API call and wrap the result in a Resource.Success if successful.
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                // Handle different types of errors, such as HTTP exceptions or general exceptions.
                when (throwable) {
                    is HttpException -> {
                        // If an HTTP exception occurs, wrap the error response in a Resource.Error.
                        Resource.Error(throwable.response()?.errorBody().toString())
                    }
                    else -> {
                        // For other exceptions, provide a localized error message in a Resource.Error.
                        Resource.Error(throwable.localizedMessage)
                    }
                }
            }
        }
    }
}






