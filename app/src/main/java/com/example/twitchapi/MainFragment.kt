package com.example.twitchapi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchapi.network.ApiInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MainFragment : Fragment() {

    @JvmField
    @Inject
    var apiInterface: ApiInterface? = null
    private lateinit var recyclerView: RecyclerView
    private var newsList: ArrayList<NewsModel.Article> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        (activity?.application as MyApplication).getComponent().inject(this)
        var call = apiInterface?.getHeadlines("3d25198a174f4978ae94dc9d88f41111", "us")
        call?.enqueue(object : Callback<NewsModel> {
            override fun onResponse(call: retrofit2.Call<NewsModel>, response: Response<NewsModel>) {
                Log.i("MainFragment", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    newsList = response.body()!!.articles
//                    println(newsList.toString())
                }
            }

            override fun onFailure(call: retrofit2.Call<NewsModel>, t: Throwable) {
                Log.i("MainFragment", "onFailure: ${t.cause}")
            }

        })

        apiInterface?.getHeadlinesObservable("3d25198a174f4978ae94dc9d88f41111", "us")?.flatMap(object : Function<NewsModel, Observable<NewsModel.Article>>{
            override fun apply(t: NewsModel?): Observable<NewsModel.Article> {
                return Observable.fromIterable(t?.articles)
            }

        })?.flatMap(object : Function<NewsModel.Article, Observable<String>>{
            override fun apply(article: NewsModel.Article?): Observable<String> {
                return Observable.just(article?.title)
            }

        })?.subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable?) {

                    }

                    override fun onNext(s: String?) {
                        println("Title $s")
                    }

                    override fun onError(e: Throwable?) {
                        Log.i("MainFragment", "onError: ${e?.cause}")
                    }

                    override fun onComplete() {
                        Log.i("MainFragment", "onComplete: All news loaded")
                    }

                })


    }

}
