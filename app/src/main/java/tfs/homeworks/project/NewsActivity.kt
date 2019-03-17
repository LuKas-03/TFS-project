package tfs.homeworks.project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast


class NewsActivity : AppCompatActivity() {

    private var menu: Menu? = null
    private var isLikedNews: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val news = intent.extras?.getParcelable<News>(ARG_NEWS)
        title = news?.title
        val content = findViewById<TextView>(R.id.newsContent)

        // TODO: не забыть убрать заглушку
        if (news?.content != null) {
            content.text = news.content
        }

        if (news?.date != null) {
            val publicationDate = findViewById<TextView>(R.id.publicationDate)
            publicationDate.text = PublicationDateBuildUtil.getPublicationDate(news.date!!)
        }

        isLikedNews = intent.extras?.getBoolean(ARG_IS_LIKED_NEWS)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.news_activity_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (isLikedNews != null && isLikedNews == true) {
            val starBtn = menu?.findItem(R.id.starButton)
            starBtn?.isEnabled = false
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
        val toast = Toast.makeText(this, getString(R.string.on_liked_message), Toast.LENGTH_LONG)
        toast.show()
    }

    companion object {
        const val ARG_NEWS = "news"
        const val ARG_IS_LIKED_NEWS = "isLiked"
    }
}
