package tfs.homeworks.project

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class NewsPageFragment : Fragment(), OnNewsItemClickListener {

    private lateinit var newsItems: List<NewsItem>
    private var isLikedNews: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsItems = it.getParcelableArrayList(ARG_NEWS)!!
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
        recyclerView.adapter = NewsAdapter(newsItems, this)
        recyclerView.addItemDecoration(NewsItemDecoration(2))
    }

    override fun onNewsItemClick(position: Int, newsItem: NewsItem, newsType: Int) {
        startActivity(NewsActivity.createIntent(requireContext(), newsItem, isLikedNews))
    }

    companion object {
        private const val ARG_NEWS = "newsCollection"
        private const val ARG_NEWS_TYPE = "newsType"

        @JvmStatic
        fun newInstance(newsItems: Array<NewsItem>, isLikedNews: Boolean) =
            NewsPageFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_NEWS, newsItems.toCollection(ArrayList()))
                    putBoolean(ARG_NEWS_TYPE, isLikedNews)
                }
            }
    }
}
