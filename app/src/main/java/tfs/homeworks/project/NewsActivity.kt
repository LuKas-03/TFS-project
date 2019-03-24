package tfs.homeworks.project

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import tfs.homeworks.project.database.NewsRoomRepository


class NewsActivity : AppCompatActivity() {

    private var menu: Menu? = null
    private var isLikedNews: Boolean? = null
    private val db = NewsRoomRepository.getInstance(this)
    private var news: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        news = intent.extras?.getParcelable<News>(ARG_NEWS)
        title = news?.title
        val content = findViewById<TextView>(R.id.newsContent)

        if (news?.content != null) {
            content.text = news!!.content
        }

        if (news?.date != null) {
            val publicationDate = findViewById<TextView>(R.id.publicationDate)
            publicationDate.text = PublicationDateBuildUtil.getPublicationDate(News.dateToCalendar(news!!.date!!))
        }

        isLikedNews = db.isLikedNews(news!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.news_activity_menu, menu)
        return true
    }

    @SuppressLint("ResourceType")
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val starBtn = menu?.findItem(R.id.starButton)
        if (isLikedNews == true) {
            starBtn?.icon = ResourcesCompat.getDrawable(resources, 17301516, null)
        }
        else {
            starBtn?.icon = ResourcesCompat.getDrawable(resources, 17301515, null)
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
            db.deleteFromLikedNews(news!!)
            val toast = Toast.makeText(this, getString(R.string.delete_from_liked_news), Toast.LENGTH_LONG)
            toast.show()
            false
        } else {
            db.addToLikedNews(news!!)
            val toast = Toast.makeText(this, getString(R.string.add_to_liked_news), Toast.LENGTH_LONG)
            toast.show()
            true
        }
        invalidateOptionsMenu()
    }

    companion object {
        const val ARG_NEWS = "news"
        const val ARG_IS_LIKED_NEWS = "isLiked"
    }
}
