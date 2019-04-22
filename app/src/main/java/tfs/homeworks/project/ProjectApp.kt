package tfs.homeworks.project

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tfs.homeworks.project.database.NewsRoomRepository
import tfs.homeworks.project.database.Repository
import tfs.homeworks.project.network.TinkoffNewsApi
import tfs.homeworks.project.network.TinkoffNewsData
import java.util.*
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.appcompat.app.AlertDialog


class ProjectApp: Application() {

    override fun onCreate() {
        super.onCreate()

        val disposable = CompositeDisposable()
        db = NewsRoomRepository.getInstance(this)

        val callAdapterFactory = RxJava2CallAdapterFactory
            .createWithScheduler(Schedulers.io())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.tinkoff.ru/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactory)
            .build()

        newsApi = retrofit.create(TinkoffNewsApi::class.java)

        loadNews(disposable)
    }


    fun loadNews(disposable: CompositeDisposable) {
        disposable.add(newsApi.GetAllNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { news -> addNews(news.payload, disposable)},
                { error -> Log.e("ERROR", "Unable to add news", error) }
            ))
    }

    private fun addNews(newsDataCollection: List<TinkoffNewsData.NewsSimpleData>, disposable: CompositeDisposable) {
        val news = mutableListOf<NewsItem>()
        for (newsData in newsDataCollection) {
            val time = Calendar.getInstance()
            time.timeInMillis = newsData.publicationDate.milliseconds
            news.add(NewsItem(newsData.id, newsData.text, time, null))
        }
        disposable
            .add(ProjectApp.db.insertNews(news)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    companion object {
        lateinit var db: Repository
            private set
        lateinit var newsApi: TinkoffNewsApi
            private set
    }

}