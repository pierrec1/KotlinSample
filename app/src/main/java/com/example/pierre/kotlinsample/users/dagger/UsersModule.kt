package com.example.pierre.kotlinsample.users.dagger

import com.example.pierre.kotlinsample.users.UsersPresenter
import com.example.pierre.kotlinsample.users.model.GetUsersAgent
import com.example.pierre.kotlinsample.users.model.GetUsersNetworkDataSource
import com.example.pierre.kotlinsample.users.model.GetUsersUseCase
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
    fun provideUserPresenter(getUsersUseCase: GetUsersUseCase): UsersPresenter = UsersPresenter(getUsersUseCase)

    @Provides
    @Singleton
    fun provideGetUsersUseCase(getUsersAgent: GetUsersAgent): GetUsersUseCase =
            GetUsersUseCase(getUsersAgent)

    @Provides
    @Singleton
    fun provideGetUsersAgent(getUsersNetworkDataSource: GetUsersNetworkDataSource): GetUsersAgent =
            GetUsersAgent(getUsersNetworkDataSource)

    @Provides
    @Singleton
    fun provideGetUsersNetworkDataSource(): GetUsersNetworkDataSource {
        return GetUsersNetworkDataSource(provideRetrofit())
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