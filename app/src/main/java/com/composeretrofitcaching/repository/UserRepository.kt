package com.composeretrofitcaching.repository

import com.composeretrofitcaching.data.local.dao.UserDao
import com.composeretrofitcaching.data.remote.api.ApiInterface
import com.composeretrofitcaching.model.UserResponse
import com.composeretrofitcaching.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val userDao: UserDao,
) {
    suspend fun insertData(userResponse: List<UserResponse>) =
        userDao.insertUser(userResponse)

    suspend fun getAllUsers(): Resource<List<UserResponse>> {
        val response = try {
            userDao.getAllUsers()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun getUserResponse(): Resource<List<UserResponse>> {
        val response = try {
            apiInterface.getUserData()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }
}