package com.example.newskotlin.presentation.presenter

import android.util.Log
import com.example.newskotlin.model.ApiServiceInterface
import com.example.newskotlin.presentation.view.NewsListView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class NewsListPresenter : MvpPresenter<NewsListView>() {

    private var pagesLoaded: Int = 1
    private var countOfPages: Int = 0
    private lateinit var nowState: ArrayList<ApiServiceInterface.Articles>

    private val newsInPage: Int = 10

    override fun onFirstViewAttach() {
        Log.e("gaf", pagesLoaded.toString())
        super.onFirstViewAttach()

        ApiServiceInterface.Factory.create().getNews(pagesLoaded, "4efaaafdb72d4925a544b61efe8bf931")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                countOfPages = it.totalResults / newsInPage
                if (it.totalResults % newsInPage != 0){
                    countOfPages++
                }
                nowState = it.articles
                viewState.setAdapter(it, this)
            }, {
                Log.e("Gaf", it.localizedMessage)
            })
    }

    fun updateNewsList(){
        if (pagesLoaded < countOfPages){
            pagesLoaded++
            Log.e("gaf", pagesLoaded.toString())
            ApiServiceInterface.Factory.create()
                .getNews(pagesLoaded, "4efaaafdb72d4925a544b61efe8bf931")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    countOfPages = it.totalResults / newsInPage
                    if (it.totalResults % newsInPage != 0) {
                        countOfPages++
                    }
                    nowState = it.articles
                }, {
                    Log.e("Gaf", it.localizedMessage)
                })
            viewState.updateAdapterData(nowState)
            Log.e("Gaf", "updateNewsList")
        }
    }
}