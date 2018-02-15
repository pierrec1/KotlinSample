package com.example.pierre.kotlinsample.users.dagger

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.example.pierre.kotlinsample.users.UsersPresenter
import com.example.pierre.kotlinsample.users.model.*
import com.example.pierre.kotlinsample.users.model.localStorage.UsersDatabase
import com.example.pierre.kotlinsample.users.model.localStorage.UsersLocalDataSource
import com.example.pierre.kotlinsample.users.model.networkStorage.UsersNetworkDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class SampleModule(private val application: Application) {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context = application
    val BASE_URL = "http://media.tictrac.com/tmp/"

    @Provides
    @Singleton
    fun provideUsersPresenter(getUsersUseCase: GetUsersUseCase, searchUsersUseCase: SearchUsersUseCase)
            : UsersPresenter = UsersPresenter(getUsersUseCase, searchUsersUseCase)

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
        return UsersLocalDataSource(provideUsersDao(provideAppDatabase(provideApplicationContext())))
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

    @Provides fun provideAppDatabase(context: Context): UsersDatabase =
            Room.databaseBuilder(context, UsersDatabase::class.java, "users-db").allowMainThreadQueries().build()

    @Provides fun provideUsersDao(database: UsersDatabase) = database.userDao()
}