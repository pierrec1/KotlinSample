package com.example.pierre.kotlinsample.users.dagger

import com.example.pierre.kotlinsample.users.UsersPresenter
import com.example.pierre.kotlinsample.users.model.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class UsersModule {

    val BASE_URL = "http://media.tictrac.com/tmp/"

    @Provides
    @Singleton
    fun provideUsersPresenter(getUsersUseCase: GetUsersUseCase, searchUsersUseCase: SearchUsersUseCase): UsersPresenter = UsersPresenter(getUsersUseCase, searchUsersUseCase)

    @Provides
    @Singleton
    fun provideGetUsersUseCase(usersAgent: UsersAgent): GetUsersUseCase =
            GetUsersUseCase(usersAgent)

    @Provides
    @Singleton
    fun provideUsersAgent(usersNetworkDataSource: UsersNetworkDataSource, usersLocalDataSource: UsersLocalDataSource): UsersAgent =
            UsersAgent(usersNetworkDataSource, usersLocalDataSource)

    @Provides
    @Singleton
    fun provideUsersNetworkDataSource(): UsersNetworkDataSource {
        return UsersNetworkDataSource(provideRetrofit())
    }

    @Provides
    @Singleton
    fun provideUsersLocalDataSource(): UsersLocalDataSource {
        return UsersLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}