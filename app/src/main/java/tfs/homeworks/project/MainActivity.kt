package tfs.homeworks.project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import tfs.homeworks.project.database.NewsRoomRepository
import tfs.homeworks.project.database.Repository

class MainActivity : AppCompatActivity() {

    private var db: Repository? = null
    private var adapter: NewsTabPagerFragmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = NewsRoomRepository.getInstance(this)
        if (savedInstanceState == null) {
            if (db?.getNews()?.isEmpty() == true) {
                addStabsToDatabase()
            }
        }
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        adapter = NewsTabPagerFragmentAdapter(supportFragmentManager, this, db!!)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
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

        db?.insertNews(news)
    }
}
