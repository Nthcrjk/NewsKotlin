package com.example.newskotlin.ui

import androidx.fragment.app.Fragment
import com.example.newskotlin.ui.fragments.DetailNewsFragment

interface OnNavigationListener {
    fun onNavigate(fragment: Fragment, addToBackStage: Boolean)
}