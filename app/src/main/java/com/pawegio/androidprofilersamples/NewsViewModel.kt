package com.pawegio.androidprofilersamples

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawegio.androidprofilersamples.model.ResponseModel
import com.pawegio.androidprofilersamples.model.Result
import com.pawegio.androidprofilersamples.network.API_KEY
import com.pawegio.androidprofilersamples.network.ApiClient
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _newsResponse = MutableLiveData<ResponseModel>()
    val newsResponse = _newsResponse.toImmutableLivedata()

    fun fetchTop() {
        viewModelScope.launch {
            val response = safeResponse {
                ApiClient.api.getLatestNews("techcrunch", API_KEY)
            }
            if (response is Result.Success) {
                _newsResponse.value = response.data!!
            }
        }
    }

    fun fetchExample() {
        viewModelScope.launch {
            val response = safeResponse {
                ApiClient.api.fetchEverything("tesla", 100, API_KEY)
            }
            if (response is Result.Success) {
                _newsResponse.value = response.data!!
            }
        }
    }
}