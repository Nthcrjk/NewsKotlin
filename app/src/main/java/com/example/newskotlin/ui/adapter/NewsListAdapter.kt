package com.example.newskotlin.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newskotlin.R

import com.example.newskotlin.TextChanger
import com.example.newskotlin.model.ApiServiceInterface
import com.example.newskotlin.ui.OnNavigationListener
import com.example.newskotlin.ui.activity.ToolBarManager
import com.example.newskotlin.ui.fragments.DetailNewsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewsListAdapter(activityContext: Context, states: ArrayList<ApiServiceInterface.Articles>) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private val activityContext: Context = activityContext
    private val inflater: LayoutInflater = LayoutInflater.from(activityContext)
    private val onNavigationListener: OnNavigationListener = activityContext as OnNavigationListener
    private val toolBarManager: ToolBarManager = activityContext as ToolBarManager
    private val states: ArrayList<ApiServiceInterface.Articles> = states

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val View = inflater.inflate(R.layout.news_list_item, parent, false)
        return ViewHolder(View)
    }

    fun getData(): ArrayList<ApiServiceInterface.Articles> {
        return states
    }

    override fun getItemCount(): Int {
        return states.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val state: ApiServiceInterface.Articles = states[position]

        if (state.content != null) {
            state.content = TextChanger.fixContent(state.content)
            state.content = TextChanger.fixText(state.content)
        }

        if (state.title != null) {
            state.title = TextChanger.fixText(state.title)
        }

        if (state.description != null) {
            state.description = TextChanger.fixText(state.description)
        }

        Picasso.with(activityContext).load(state.urlToImage).into(holder.headerItemImage)
        holder.headerItemTitle.text = state.title
        holder.headerItemDescription.text = state.description

        if (state.author == null) {
            holder.headerItemAuthorWord1.text = ""
            holder.headerItemAuthorWord2.text = ""
        } else {
            holder.headerItemAuthorWord2.text = state.author
            holder.headerItemAuthorWord1.text = "Author: "
        }

        holder.headerItemPublished.text = state.publishedAt
        holder.itemView.setOnClickListener {
            onNavigationListener.onNavigate(DetailNewsFragment(state), true)
            toolBarManager.showBackButton()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerItemImage: ImageView = itemView.header_item_image
        val headerItemTitle: TextView = itemView.header_item_title
        val headerItemDescription: TextView = itemView.header_item_description
        val headerItemAuthorWord1: TextView = itemView.header_item_author_word1
        val headerItemAuthorWord2: TextView = itemView.header_item_author_word2
        val headerItemPublished: TextView = itemView.header_item_published
    }
}