package tfs.homeworks.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tfs.homeworks.project.network.NewsLoadHelper


class NewsPageFragment : androidx.fragment.app.Fragment(), OnNewsItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private var dataSet: ArrayList<Any>  = arrayListOf()
    private var recyclerViewAdapter: NewsAdapter = NewsAdapter(dataSet, this)
    private var isLikedNews: Boolean = false
    private var isNetworkConnect: Boolean = false
    private val disposable = CompositeDisposable()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isLikedNews = it.getBoolean(ARG_NEWS_TYPE)
            isNetworkConnect = it.getBoolean(ARG_NETWORK)
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
            disposable.add(ProjectApp.db.getLikedNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { createDataSet(ArrayList(it))},
                    {Log.e("ERROR", "Unable to load liked news collection", it)})
            )
        } else {
            disposable.add(ProjectApp.db.getNews()
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

        val recyclerView = view.findViewById<RecyclerView>(R.id.newsRecyclerView)

        recyclerView.adapter = recyclerViewAdapter
        recyclerView.addItemDecoration(NewsItemDecoration(2))

        swipeRefreshLayout = view.findViewById(R.id.refresh)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onNewsItemClick(position: Int, newsItem: NewsItem, newsType: Int) {
        startActivity(NewsActivity.createIntent(requireContext(), newsItem, isLikedNews))
    }

    override fun onRefresh() {
        NewsLoadHelper.loadNews(disposable) {swipeRefreshLayout.isRefreshing = false}
    }

    companion object {
        private const val ARG_NEWS_TYPE = "newsType"
        private const val ARG_NETWORK = "hasNetworkConnect"

        @JvmStatic
        fun newInstance(isLikedNews: Boolean, hasNetworkConnect: Boolean) =
            NewsPageFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_NEWS_TYPE, isLikedNews)
                    putBoolean(ARG_NETWORK, hasNetworkConnect)
                }
            }
    }
}
