package com.example.newskotlin.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newskotlin.R
import com.example.newskotlin.model.ApiServiceInterface
import com.example.newskotlin.ui.OnNavigationListener
import com.example.newskotlin.ui.activity.ToolBarManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_news.view.*


class DetailNewsFragment(state: ApiServiceInterface.Articles) : Fragment() {

    private var articles: ApiServiceInterface.Articles = state
    private lateinit var onNavigationListener: OnNavigationListener
    private lateinit var activityContext: Context
    private lateinit var toolBarManager: ToolBarManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
        onNavigationListener = context as OnNavigationListener
        toolBarManager = context as ToolBarManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = LayoutInflater.from(activityContext).inflate(R.layout.fragment_detail_news, container, false)
        toolBarManager.showBackButton()
        Picasso.with(activityContext).load(articles.urlToImage).into(view.detail_item_image)
        view.detail_item_title.text = articles.title
        view.detail_item_published.text = articles.publishedAt
        view.detail_item_description.text = articles.description
        view.detail_item_author.text = articles.author
        view.detail_item_content.text = articles.content


        var link: String = "<a href=\"" + articles.url +"\"> в источнике</a>"
        var txt: Spanned = Html.fromHtml(link, null, null)
        view.detail_item_content.append(txt)
        view.detail_item_content.linksClickable = true
        view.detail_item_content.movementMethod = LinkMovementMethod.getInstance()

        return view
    }
}