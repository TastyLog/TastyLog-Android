package com.knu.network.api

import retrofit2.http.POST

interface ExampleApi {
    @POST("api/v1/example")
    suspend fun example(): String
}
