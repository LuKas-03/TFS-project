package tfs.homeworks.project

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class NewsPageFragment : Fragment(), OnNewsItemClickListener {

    private var dataSet: MutableList<Any>  = mutableListOf()
    private var isLikedNews: Boolean = false
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isLikedNews = it.getBoolean(ARG_NEWS_TYPE)
        }

        createDataSet()
    }

    private fun createDataSet() {
        if (isLikedNews) {
            disposable.add(MainActivity.getDatabaseInstance().getLikedNews().toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t1, _ -> createDataSet(t1) })
        } else {
            disposable.add(MainActivity.getDatabaseInstance().getNews().toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t1, _ -> createDataSet(t1) })
        }
    }

    private fun createDataSet(newsItems: MutableList<NewsItem>) {
        if (newsItems.isEmpty()) {
            return
        }
        newsItems.sortByDescending { x -> NewsItem.dateToCalendar(x.date!!) }

        var currentDate = newsItems[0].date
        dataSet = mutableListOf(NewsGroupHeader(newsItems[0].getDateInLongFormat()!!))

        for (newsItem in newsItems) {
            if (newsItem.date != currentDate) {
                dataSet.add(NewsGroupHeader(newsItem.getDateInLongFormat()!!))
                currentDate = newsItem.date
            }
            dataSet.add(newsItem)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView =view.findViewById<RecyclerView>(R.id.newsRecyclerView)
        recyclerView.adapter = NewsAdapter(dataSet.toTypedArray(), this)
        recyclerView.addItemDecoration(NewsItemDecoration(2))
    }

    override fun onNewsItemClick(position: Int, newsItem: NewsItem, newsType: Int) {
        startActivity(NewsActivity.createIntent(requireContext(), newsItem, isLikedNews))
    }

    companion object {
        private const val ARG_NEWS_TYPE = "newsType"

        @JvmStatic
        fun newInstance(isLikedNews: Boolean) =
            NewsPageFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_NEWS_TYPE, isLikedNews)
                }
            }
    }
}
