package com.composeretrofitcaching.model

import androidx.room.Entity

@Entity(tableName = "user_tbl", primaryKeys = ["userId"])
data class UserResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
