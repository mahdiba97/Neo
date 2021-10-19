package com.mahdiba97.notes.di

import android.content.Context
import androidx.room.Room
import com.mahdiba97.notes.db.AppDatabase
import com.mahdiba97.notes.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideRepository(appDatabase: AppDatabase) = Repository(appDatabase)

  @Singleton
  @Provides
  fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
    context, AppDatabase::class.java, "notes.db"
  ).build()

}