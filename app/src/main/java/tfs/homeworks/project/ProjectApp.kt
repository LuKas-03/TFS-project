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
import io.reactivex.Completable
import io.reactivex.Flowable


class ProjectApp: Application() {
    private lateinit var disposable: CompositeDisposable
    override fun onCreate() {
        super.onCreate()

        disposable = CompositeDisposable()
        db = NewsRoomRepository.getInstance(this)

        val callAdapterFactory = RxJava2CallAdapterFactory
            .createWithScheduler(Schedulers.io())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.tinkoff.ru/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactory)
            .build()

        newsApi = retrofit.create(TinkoffNewsApi::class.java)

        deleteOldNews(disposable)
    }

    fun deleteOldNews(disposable: CompositeDisposable) {
        disposable.add(db.getNews()
            .sorted()
            .map { it.sortedBy { newsItem -> newsItem.date }.drop(100) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { news ->
                    disposable.add(db.deleteNews(news)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                ) },
                { error -> Log.e("ERROR", "Unable to delete news", error) }
            ))
    }

    companion object {
        lateinit var db: Repository
            private set
        lateinit var newsApi: TinkoffNewsApi
            private set
    }

}