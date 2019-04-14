package tfs.homeworks.project

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import tfs.homeworks.project.database.Repository


class NewsTabPagerFragmentAdapter(fm: androidx.fragment.app.FragmentManager?, context: Context) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {

    private val tabTitles = arrayOf(
        context.getString(R.string.last_news_tab_title),
        context.getString(R.string.liked_news_tab_title)
    )

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return NewsPageFragment.newInstance(position == LIKED_NEWS)
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return androidx.viewpager.widget.PagerAdapter.POSITION_NONE
    }

    companion object {
        const val LATEST_NEWS = 0
        const val LIKED_NEWS = 1
    }
}