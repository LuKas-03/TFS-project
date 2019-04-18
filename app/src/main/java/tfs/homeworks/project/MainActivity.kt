package tfs.homeworks.project

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tfs.homeworks.project.database.NewsRoomRepository
import tfs.homeworks.project.database.Repository

class MainActivity : AppCompatActivity() {

    private var adapter: NewsTabPagerFragmentAdapter? = null
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = NewsRoomRepository.getInstance(this)
        val viewPager = findViewById<androidx.viewpager.widget.ViewPager>(R.id.viewPager)
        adapter = NewsTabPagerFragmentAdapter(supportFragmentManager, this)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onStart() {
        super.onStart()

        disposable.add(getDatabaseInstance().getNews()
            .firstOrError()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t1 -> if ( t1.isEmpty()) addStabsToDatabase() },
                { error -> Log.e("ERROR", "Unable to add stabs", error) }))
    }

    override fun onStop() {
        super.onStop()

        disposable.clear()
    }

    override fun onRestart() {
        super.onRestart()
        adapter?.notifyDataSetChanged()
    }

    private fun addStabsToDatabase() {
        val news = listOf (
            NewsItem("Last news #1", "This is not interesting news", "2019-03-17", getString(R.string.stub)),
            NewsItem("Last news #2", "This is not interesting news", "2019-03-16", getString(R.string.stub)),
            NewsItem("Last news #3", "This is not interesting news", "2019-03-16", getString(R.string.stub)),
            NewsItem("Best news #1", "This is very interesting news", "2019-03-12", getString(R.string.stub)),
            NewsItem("Best news #2", "This is very interesting news", "2018-12-01", getString(R.string.stub))
        )

        disposable.add(getDatabaseInstance().insertNews(news)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    companion object {
        private var db: Repository? = null
        fun getDatabaseInstance(): Repository {
            return db!!
        }
    }
}
