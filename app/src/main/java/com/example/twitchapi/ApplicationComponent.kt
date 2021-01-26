package com.example.twitchapi

import android.app.Activity
import android.app.Application
import com.example.twitchapi.network.ApiInterface
import com.example.twitchapi.network.ApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ApiModule::class])
interface ApplicationComponent {

    fun inject(target: MainFragment?)
//    fun getNetworkService(): ApiInterface
}