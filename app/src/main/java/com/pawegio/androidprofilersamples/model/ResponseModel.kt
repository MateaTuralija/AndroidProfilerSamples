package com.pawegio.androidprofilersamples.model

data class ResponseModel(
        val status: String,
        val totalResults: Int,
        val articles: List<Article>
)