package tfs.homeworks.project

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private var adapter: NewsTabPagerFragmentAdapter? = null
    private var isNetworkConnect: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            isNetworkConnect = hasConnection()
            if (!isNetworkConnect) {
                showDialog()
            }
        }
        else if (savedInstanceState.containsKey("isNetworkConnect")) {
            isNetworkConnect = savedInstanceState.getBoolean("isNetworkConnect")
        }

        val viewPager = findViewById<androidx.viewpager.widget.ViewPager>(R.id.viewPager)
        adapter = NewsTabPagerFragmentAdapter(supportFragmentManager, this, isNetworkConnect)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        if (!hasConnection()) {
            showDialog()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("isNetworkConnect", isNetworkConnect)
    }

    override fun onRestart() {
        super.onRestart()
        adapter?.notifyDataSetChanged()
    }

    private fun hasConnection(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(this)
            .setMessage("Доступ к интернету отключен, функционал приложения ограничен.")
            .setPositiveButton("OK", null)
            .create()
        dialog.show()
    }
}
