package tfs.homeworks.project

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tfs.homeworks.project.database.NewsRoomRepository
import tfs.homeworks.project.database.Repository

class MainActivity : AppCompatActivity() {

    private var adapter: NewsTabPagerFragmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<androidx.viewpager.widget.ViewPager>(R.id.viewPager)
        adapter = NewsTabPagerFragmentAdapter(supportFragmentManager, this)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onRestart() {
        super.onRestart()
        adapter?.notifyDataSetChanged()
    }


}
