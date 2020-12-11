package com.app4web.asdzendo.todo.di

import android.content.Context
import com.app4web.asdzendo.todo.database.FactDatabase
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import com.app4web.asdzendo.todo.database.FactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactModule {
/*
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create(CharacterService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService) = CharacterRemoteDataSource(characterService)
*/
    @Singleton
    @Provides
    fun provideFactDatabase(@ApplicationContext appContext: Context) = FactDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideFactDatabaseDao(factDatabase: FactDatabase) = factDatabase.factDatabaseDao()

    @Singleton
    @Provides
    fun provideFactRepository(factDatabaseDao: FactDatabaseDao) =  FactRepository(factDatabaseDao)
}