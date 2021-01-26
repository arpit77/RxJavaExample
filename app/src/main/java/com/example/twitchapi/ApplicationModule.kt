package com.example.twitchapi

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.twitchapi.network.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application?) {

    @Provides
    @Singleton
    fun getAppContext(): Context? {
        return application
    }
}