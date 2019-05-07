package tfs.homeworks.project.network

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tfs.homeworks.project.NewsItem
import tfs.homeworks.project.ProjectApp
import java.util.*

object NewsLoadHelper {

    fun loadNews(disposable: CompositeDisposable, doOnComplete: (() -> Unit)? = null) {
        disposable.add(
            ProjectApp.newsApi.GetAllNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    { news -> addNews(news.payload, disposable, doOnComplete)},
                    { error -> Log.e("ERROR", "Unable to load news", error) }
                ))
    }

    private fun addNews(newsDataCollection: List<TinkoffNewsData.NewsSimpleData>, disposable: CompositeDisposable, doOnComplete: (() -> Unit)?) {
        val news = mutableListOf<NewsItem>()
        for (newsData in newsDataCollection) {
            val time = Calendar.getInstance()
            time.timeInMillis = newsData.publicationDate.milliseconds
            news.add(NewsItem(newsData.id, newsData.text, time, null))
        }
        disposable
            .add(
                if (doOnComplete != null) {
                ProjectApp.db.insertNews(news)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(doOnComplete)
                }
                else {
                    ProjectApp.db.insertNews(news)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                })
    }
}