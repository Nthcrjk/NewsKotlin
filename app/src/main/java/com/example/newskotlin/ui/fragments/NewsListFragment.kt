package com.example.newskotlin.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newskotlin.R
import com.example.newskotlin.model.ApiServiceInterface
import com.example.newskotlin.presentation.presenter.NewsListPresenter
import com.example.newskotlin.presentation.view.NewsListView
import com.example.newskotlin.ui.OnNavigationListener
import com.example.newskotlin.ui.activity.ToolBarManager
import com.example.newskotlin.ui.adapter.NewsListAdapter
import kotlinx.android.synthetic.main.fragment_news_list.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class NewsListFragment : MvpAppCompatFragment(), NewsListView {

    private val presenter: NewsListPresenter by moxyPresenter { NewsListPresenter() }
    private lateinit var onNavigationListener: OnNavigationListener
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var toolBarManager: ToolBarManager
    private lateinit var activityContext: Context
    private lateinit var adapter: NewsListAdapter
    private lateinit var manager: LinearLayoutManager

    private var loading: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
        onNavigationListener = context as OnNavigationListener
        toolBarManager = context as ToolBarManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_news_list, container, false)
        newsRecyclerView = view.newsRecycleView
        toolBarManager.hideBackButton()
        return view
    }

    override fun setAdapter(news: ApiServiceInterface.NewsModel, presenter: NewsListPresenter) {
        manager = object: LinearLayoutManager(activityContext){
            override fun scrollVerticallyBy(
                dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
                var scrollRange: Int = super.scrollVerticallyBy(dy, recycler, state)
                var overScroll: Int = dy - scrollRange
                if (overScroll > 0 && loading){
                    loading = false
                    activity?.window?.decorView?.postDelayed({ loading = true }, 1000)
                    presenter.updateNewsList()
                }
                return scrollRange
            }
        }
            adapter = NewsListAdapter(activityContext, news.articles)
            newsRecyclerView.layoutManager = manager
            newsRecyclerView.adapter = adapter
    }

    override fun updateAdapterData(news: ArrayList<ApiServiceInterface.Articles>) {
        adapter.getData().addAll(news)
        activity?.window?.decorView?.postDelayed({ adapter.notifyDataSetChanged() }, 0)
        Log.e("Gaf", "AdapterUpdate")
    }
}