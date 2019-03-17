package tfs.homeworks.project

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class NewsTabPagerFragmentAdapter(fm: FragmentManager?, context: Context) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf(
        context.getString(R.string.last_news_tab_title),
        context.getString(R.string.liked_news_tab_title)
    )

    override fun getItem(position: Int): Fragment {
        return NewsPageFragment.newInstance(getNews(position), position == LIKED_NEWS)
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    private fun getNews(newsType: Int): List<News> {
        return if (newsType == LATEST_NEWS) {
            listOf (
                News("Last news #1", "This is not interesting news", "17.03.2019", null),
                News("Last news #2", "This is not interesting news", "16.03.2019", null),
                News("Last news #3", "This is not interesting news", "16.03.2019", null)
            )
        } else {
            listOf(
                News("Best news #1", "This is very interesting news", "12.03.2019", null),
                News("Best news #2", "This is very interesting news", "01.12.2018", null)
            )
        }
    }

    companion object {
        const val LATEST_NEWS = 0
        const val LIKED_NEWS = 1
    }
}