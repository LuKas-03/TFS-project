package tfs.homeworks.project.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface TinkoffNewsApi {
    @GET("news")
    fun GetAllNews(): Single<TinkoffNewsData>

    @GET("news_content")
    fun GetNewsData(@Query("id") id: String): Single<TinkoffNewsContent>
}