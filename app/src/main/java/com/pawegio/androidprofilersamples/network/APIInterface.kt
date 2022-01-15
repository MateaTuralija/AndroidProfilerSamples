package com.pawegio.androidprofilersamples.network

import retrofit2.http.GET
import com.pawegio.androidprofilersamples.model.ResponseModel
import retrofit2.http.Query

const val API_KEY = "3178d94095de47c291a00a46028a066f"

interface APIInterface {

    @GET("top-headlines")
    suspend fun getLatestNews(@Query("sources") source: String, @Query("apiKey") apiKey: String): ResponseModel

    @GET("everything")
    suspend fun fetchEverything(@Query("q") query: String, @Query("from") from: String, @Query("apiKey") apiKey: String): ResponseModel

}