package com.example.newskotlin
import android.text.Html

object TextChanger {
    fun fixText(text: String): String {
        var newText = Html.fromHtml(text).toString()
        newText = newText.replace("[\\s]+", " ")
        return newText
    }
    fun fixContent(content: String): String{
        var newContent = content
        newContent = newContent.replace("\\[[+]\\d+\\schars\\]".toRegex(), "")
        return newContent
    }
}