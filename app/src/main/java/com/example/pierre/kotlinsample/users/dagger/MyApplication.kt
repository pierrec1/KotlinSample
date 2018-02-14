package com.example.pierre.kotlinsample.users.dagger

import android.app.Application

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    private fun initDagger(app: MyApplication): AppComponent =
            DaggerAppComponent.builder()
                    .sampleModule(SampleModule(app))
                    .build()

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }
}