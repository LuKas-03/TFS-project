package tfs.homeworks.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tfs.homeworks.project.database.NewsRoomRepository


class NewsActivity : AppCompatActivity() {

    private var isLikedNews: Boolean = false
    private val db = ProjectApp.db
    private var newsItem: NewsItem? = null
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        newsItem = intent.extras?.getParcelable(ARG_NEWS)
        title = newsItem?.title
        val content = findViewById<TextView>(R.id.newsContent)

        if (newsItem?.content != null) {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
            content.text = HtmlCompat.fromHtml(newsItem!!.content!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
            findViewById<TextView>(R.id.publicationDate).text = newsItem!!.getDateInLongFormat()
        }
        else {
            loadNews(newsItem?.id!!, newsItem!!, content)
        }
    }

    override fun onStart() {
        super.onStart()

        disposable.add(db.isLikedNews(newsItem!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isLikedNews = it
                    invalidateOptionsMenu()
                },
                { Log.e("ERROR", "Unable to determine type of news")}))
    }

    override fun onStop() {
        super.onStop()

        disposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_activity_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val starBtn = menu?.findItem(R.id.starButton)
        if (isLikedNews) {
            starBtn?.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_star_white_32dp, null)
        }
        else {
            starBtn?.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_star_off_white_32dp, null)
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
        if (isLikedNews) {
            disposable.add(db.deleteFromLikedNews(newsItem!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showToast(getString(R.string.delete_from_liked_news))
                    isLikedNews = false
                    invalidateOptionsMenu()
                })
        } else {
            disposable.add(db.addToLikedNews(newsItem!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showToast(getString(R.string.add_to_liked_news))
                    isLikedNews = true
                    invalidateOptionsMenu()
                })
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }

    private fun loadNews(id: Int, newsItem: NewsItem, textView: TextView) {
        disposable.add(ProjectApp.newsApi.GetNewsData(id.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    textView.text = HtmlCompat.fromHtml(it.payload.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    findViewById<TextView>(R.id.publicationDate).text = newsItem.getDateInLongFormat()
                    newsItem.content = it.payload.content
                    addContentToNews(newsItem)
                },
                {
                    Log.e("ERROR", "Unable to load news content")
                }
            ))
    }

    private fun addContentToNews(newsItem: NewsItem) {
        disposable.add(ProjectApp.db.updateNews(newsItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    companion object {
        const val ARG_NEWS = "newsItem"
        private const val ARG_IS_LIKED_NEWS = "isLiked"

        fun createIntent(context: Context, newsItem: NewsItem, isLikedNews: Boolean): Intent {
            return Intent(context, NewsActivity::class.java)
                .putExtra(ARG_NEWS, newsItem)
                .putExtra(ARG_IS_LIKED_NEWS, isLikedNews)
        }
    }
}
