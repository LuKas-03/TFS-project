package tfs.homeworks.project

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import tfs.homeworks.project.database.NewsRoomRepository


class NewsActivity : AppCompatActivity() {

    private var isLikedNews: Boolean? = null
    private val db = NewsRoomRepository.getInstance(this)
    private var newsItem: NewsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        newsItem = intent.extras?.getParcelable(ARG_NEWS)
        title = newsItem?.title
        val content = findViewById<TextView>(R.id.newsContent)

        if (newsItem?.content != null) {
            content.text = newsItem!!.content
        }

        if (newsItem?.date != null) {
            val publicationDate = findViewById<TextView>(R.id.publicationDate)
            publicationDate.text = PublicationDateBuildUtil.getPublicationDate(NewsItem.dateToCalendar(newsItem!!.date!!))
        }

        isLikedNews = db.isLikedNews(newsItem!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_activity_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val starBtn = menu?.findItem(R.id.starButton)
        if (isLikedNews == true) {
            starBtn?.icon = ResourcesCompat.getDrawable(resources, android.R.drawable.star_big_on, null)
        }
        else {
            starBtn?.icon = ResourcesCompat.getDrawable(resources, android.R.drawable.star_big_off, null)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.starButton -> {
                onStarButtonClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onStarButtonClicked() {
        isLikedNews = if (isLikedNews!!) {
            db.deleteFromLikedNews(newsItem!!)
            val toast = Toast.makeText(this, getString(R.string.delete_from_liked_news), Toast.LENGTH_LONG)
            toast.show()
            false
        } else {
            db.addToLikedNews(newsItem!!)
            val toast = Toast.makeText(this, getString(R.string.add_to_liked_news), Toast.LENGTH_LONG)
            toast.show()
            true
        }
        invalidateOptionsMenu()
    }

    companion object {
        const val ARG_NEWS = "newsItem"
        private const val ARG_IS_LIKED_NEWS = "isLiked"

        fun createIntent(context: Context, newsItem: NewsItem, isLikedNews: Boolean): Intent {
            return Intent(context, NewsActivity::class.java)
                .putExtra(NewsActivity.ARG_NEWS, newsItem)
                .putExtra(NewsActivity.ARG_IS_LIKED_NEWS, isLikedNews)
        }
    }
}
