package com.composeretrofitcaching.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeretrofitcaching.model.UserResponse
import com.composeretrofitcaching.repository.UserRepository
import com.composeretrofitcaching.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    private var _getUserData: MutableLiveData<List<UserResponse>> =
        MutableLiveData<List<UserResponse>>()
    var getUserData: LiveData<List<UserResponse>> = _getUserData

    private var _getUserDataOffline: MutableLiveData<List<UserResponse>> =
        MutableLiveData<List<UserResponse>>()
    var getUserDataOffline: LiveData<List<UserResponse>> = _getUserDataOffline

    init {
        viewModelScope.launch {
            getUserData()
        }
    }

    suspend fun getUserData(): Resource<List<UserResponse>> {
        val result = userRepository.getUserResponse()
        if (result is Resource.Loading) {
            isLoading.value = false
        } else if (result is Resource.Success) {
            isLoading.value = true
            _getUserData.value = result.data!!
            Log.e("data_log", result.data.toString())
            userRepository.insertData(_getUserData.value!!)
            _getUserDataOffline.value = userRepository.getAllUsers().data!!
            Log.e("message_01_log", result.message.toString())
            Log.e("immd_log", _getUserDataOffline.value.toString())
        } else if (result is Resource.Error) {
            isLoading.value = true
            Log.e("message_02_log", result.message.toString())
            _getUserDataOffline.value = userRepository.getAllUsers().data!!
        }

        return result
    }
}