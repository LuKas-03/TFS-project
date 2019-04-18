package tfs.homeworks.project

import android.app.Application
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tfs.homeworks.project.database.NewsRoomRepository
import tfs.homeworks.project.database.Repository

class ProjectApp: Application() {

    override fun onCreate() {
        super.onCreate()

        val disposable = CompositeDisposable()
        db = NewsRoomRepository.getInstance(this)
        disposable.add(ProjectApp.db.getNews()
            .firstOrError()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t1 -> if ( t1.isEmpty()) addStabsToDatabase(disposable) },
                { error -> Log.e("ERROR", "Unable to add stabs", error) }))
    }

    private fun addStabsToDatabase(disposable: CompositeDisposable) {
        val news = listOf (
            NewsItem("Last news #1", "This is not interesting news", "2019-03-17", getString(R.string.stub)),
            NewsItem("Last news #2", "This is not interesting news", "2019-03-16", getString(R.string.stub)),
            NewsItem("Last news #3", "This is not interesting news", "2019-03-16", getString(R.string.stub)),
            NewsItem("Best news #1", "This is very interesting news", "2019-03-12", getString(R.string.stub)),
            NewsItem("Best news #2", "This is very interesting news", "2018-12-01", getString(R.string.stub))
        )

        disposable
            .add(ProjectApp.db.insertNews(news)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    companion object {
        lateinit var db: Repository
            private set
    }

}