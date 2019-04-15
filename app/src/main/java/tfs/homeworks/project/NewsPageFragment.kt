package tfs.homeworks.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class NewsPageFragment : androidx.fragment.app.Fragment(), OnNewsItemClickListener {

    private var dataSet: ArrayList<Any>  = arrayListOf()
    private var recyclerViewAdapter: NewsAdapter = NewsAdapter(dataSet, this)
    private var newsItems: MutableList<NewsItem>  = mutableListOf()
    private var isLikedNews: Boolean = false
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isLikedNews = it.getBoolean(ARG_NEWS_TYPE)
        }
    }

    override fun onStart() {
        super.onStart()
        createDataSet()
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun createDataSet() {
        if (isLikedNews) {
            disposable.add(MainActivity.getDatabaseInstance().getLikedNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { createDataSet(ArrayList(it))},
                    {Log.e("ERROR", "Unable to load liked news collection", it)})
            )
        } else {
            disposable.add(MainActivity.getDatabaseInstance().getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { createDataSet(ArrayList(it))},
                    {Log.e("ERROR", "Unable to load all news collection", it)})
            )
        }
    }

    private fun createDataSet(newsItems: MutableList<NewsItem>) {
        if (newsItems.isEmpty()) {
            return
        }
        newsItems.sortByDescending { x -> NewsItem.dateToCalendar(x.date!!) }

        var currentDate = newsItems[0].date
        dataSet = arrayListOf(NewsGroupHeader(newsItems[0].getDateInLongFormat()!!))

        for (newsItem in newsItems) {
            if (newsItem.date != currentDate) {
                dataSet.add(NewsGroupHeader(newsItem.getDateInLongFormat()!!))
                currentDate = newsItem.date
            }
            dataSet.add(newsItem)
        }

        recyclerViewAdapter.dataset = dataSet
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView =view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.newsRecyclerView)

        recyclerView.adapter = recyclerViewAdapter
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
