package tfs.homeworks.project

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import tfs.homeworks.project.database.Repository


class NewsTabPagerFragmentAdapter(fm: FragmentManager?, context: Context, db: Repository) : FragmentStatePagerAdapter(fm) {

    private val database = db
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

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    private fun getNews(newsType: Int): Array<NewsItem> {
        return if (newsType == LATEST_NEWS) {
            database.getNews()
        } else {
            database.getLikedNews()
        }
    }

    companion object {
        const val LATEST_NEWS = 0
        const val LIKED_NEWS = 1
    }
}