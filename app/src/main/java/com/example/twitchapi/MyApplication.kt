package com.example.twitchapi

import android.app.Application
import com.example.twitchapi.network.ApiInterface
import com.example.twitchapi.network.ApiModule
import javax.inject.Inject

class MyApplication : Application() {

    private lateinit var component : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .apiModule(ApiModule())
            .build()

    }

    fun getComponent(): ApplicationComponent {
        return component
    }
}