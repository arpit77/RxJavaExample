package com.example.twitchapi.network

import com.example.twitchapi.NewsModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


public interface ApiInterface {

    @GET("top-headlines")
    fun getHeadlines(@Query("apiKey") apiKey: String,
                     @Query("country") country: String) : Call<NewsModel>


    @GET("top-headlines")
    fun getHeadlinesObservable(@Query("apiKey") apiKey: String,
                     @Query("country") country: String) : Observable<NewsModel>
}