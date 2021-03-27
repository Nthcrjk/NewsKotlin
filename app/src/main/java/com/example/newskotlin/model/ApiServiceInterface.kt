package com.example.newskotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {
    data class Source(@SerializedName("id") @Expose val id: String,
                      @SerializedName("name") @Expose val name: String)

    data class Articles (@SerializedName("source") @Expose val source: Source,
                         @SerializedName("author") @Expose val author: String,
                         @SerializedName("title") @Expose var title: String,
                         @SerializedName("description") @Expose var description: String,
                         @SerializedName("url") @Expose val url: String,
                         @SerializedName("urlToImage") @Expose val urlToImage: String,
                         @SerializedName("publishedAt") @Expose val publishedAt: String,
                         @SerializedName("content") @Expose var content: String)

    data class NewsModel (@SerializedName("status") @Expose val status: String,
                          @SerializedName("totalResults") @Expose val totalResults: Int,
                          @SerializedName("articles") @Expose val articles: ArrayList<Articles>)

    @GET("everything?q=anime&pageSize=10")
    fun getNews(@Query("page") page: Int, @Query("apiKey") apiKey: String): Observable<NewsModel>

    companion object Factory {
        fun create(): ApiServiceInterface {
            val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .build()
            return retrofit.create(ApiServiceInterface::class.java)
        }
    }

}