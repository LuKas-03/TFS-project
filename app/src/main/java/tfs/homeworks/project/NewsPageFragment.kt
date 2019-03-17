package tfs.homeworks.project

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


private const val ARG_NEWS = "newsCollection"
private const val ARG_NEWS_TYPE = "newsType"

class NewsPageFragment : Fragment(), OnNewsItemClickListener {

    private var newsList: List<News>? = null
    private var isLikedNews: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsList = it.getParcelableArray(ARG_NEWS).map { i -> i as News}
            isLikedNews = it.getBoolean(ARG_NEWS_TYPE)
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
        recyclerView.adapter = NewsAdapter(newsList!!, this)
    }

    override fun onNewsItemClick(position: Int, news: News, newsType: Int) {
        val intent = Intent(context, NewsActivity::class.java)
        intent.putExtra(NewsActivity.ARG_NEWS, news)
        intent.putExtra(NewsActivity.ARG_IS_LIKED_NEWS, isLikedNews)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(news: List<News>, isLikedNews: Boolean) =
            NewsPageFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(ARG_NEWS, news.toTypedArray())
                    putBoolean(ARG_NEWS_TYPE, isLikedNews)
                }
            }
    }
}
