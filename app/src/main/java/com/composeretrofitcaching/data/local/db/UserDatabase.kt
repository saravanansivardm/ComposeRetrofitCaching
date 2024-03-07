package com.composeretrofitcaching.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.composeretrofitcaching.data.local.dao.UserDao
import com.composeretrofitcaching.model.UserResponse

@Database(entities = [UserResponse::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}