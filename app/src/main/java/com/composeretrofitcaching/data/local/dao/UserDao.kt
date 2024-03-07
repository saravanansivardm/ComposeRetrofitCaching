package com.composeretrofitcaching.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.composeretrofitcaching.model.UserResponse

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userResponse: List<UserResponse>)

    @Query("SELECT * FROM user_tbl")
    fun getAllUsers(): List<UserResponse>

}