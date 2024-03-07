package com.composeretrofitcaching.di

import android.content.Context
import androidx.room.Room
import com.composeretrofitcaching.data.local.dao.UserDao
import com.composeretrofitcaching.data.local.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideDAO(noteDatabase: UserDatabase): UserDao {
        return noteDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, UserDatabase::class.java, "user_tbl"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

}