package com.example.newskotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.newskotlin.R
import com.example.newskotlin.ui.OnNavigationListener
import com.example.newskotlin.ui.fragments.NewsListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnNavigationListener, ToolBarManager {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolBar()
        onNavigate(NewsListFragment(), false)
    }

    override fun setToolBar(){
        tool_bar.setNavigationIcon(R.drawable.baseline_reply_black_18dp)
        tool_bar.title = ""
        tool_bar.setCollapseIcon(R.drawable.baseline_search_black_18dp)
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tool_bar.setNavigationOnClickListener{
            onBackPressed();
        }
        tool_bar.title = ""
    }

    override fun hideBackButton() {
        tool_bar.navigationIcon = null
    }

    override fun showBackButton() {
        tool_bar.setNavigationIcon(R.drawable.baseline_reply_black_18dp)
    }

    override fun onNavigate(fragment: Fragment, isAddToBackStage: Boolean) {
        var fTrance: FragmentTransaction = supportFragmentManager.beginTransaction()
        fTrance.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        fTrance.replace(R.id.content, fragment)
        if (isAddToBackStage){
            fTrance.addToBackStack(null)
        }
        fTrance.commit()
    }
}