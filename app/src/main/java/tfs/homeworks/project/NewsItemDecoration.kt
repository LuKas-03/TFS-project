package tfs.homeworks.project

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class NewsItemDecoration(offset: Int) : RecyclerView.ItemDecoration() {

    private val offsetDp = (offset * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = offsetDp
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val count = parent.childCount
        val width = parent.width

        for (i in 0 until count - 1) {
            val child = parent.getChildAt(i)
            val bottom = child.bottom
            val paint = Paint()
            paint.color = Color.GRAY

            c.drawRect(0F, bottom.toFloat(), width.toFloat(), (bottom + offsetDp).toFloat(), paint)
        }
    }
}