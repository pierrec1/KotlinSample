package com.example.pierre.kotlinsample.users.dagger

import com.example.pierre.kotlinsample.users.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SampleModule::class])

interface AppComponent {
    fun inject(mainActivity: MainActivity)
}
