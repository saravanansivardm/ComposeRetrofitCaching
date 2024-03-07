package com.composeretrofitcaching.data.remote.api

import com.composeretrofitcaching.model.UserResponse
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ApiInterface {

//    @GET("photos")
    @GET("todos")
    suspend fun getUserData(): List<UserResponse>
}