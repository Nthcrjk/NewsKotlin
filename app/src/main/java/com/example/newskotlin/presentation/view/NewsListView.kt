package com.example.newskotlin.presentation.view

import com.example.newskotlin.model.ApiServiceInterface
import com.example.newskotlin.presentation.presenter.NewsListPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface NewsListView : MvpView {
    fun setAdapter(news: ApiServiceInterface.NewsModel, presenter: NewsListPresenter)
    fun updateAdapterData(news: ArrayList<ApiServiceInterface.Articles>)
}