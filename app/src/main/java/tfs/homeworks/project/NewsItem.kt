package tfs.homeworks.project

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import tfs.homeworks.project.PublicationDateBuildUtil.Companion.getPublicationDate
import java.text.SimpleDateFormat
import java.util.*

@Entity
class NewsItem() : Parcelable {

    constructor(parcel: Parcel) : this() {
        val data = arrayOfNulls<String>(4)
        parcel.readStringArray(data)
        id = data[0]!!.toInt()
        title = data[1]
        date = data[2]
        content = data[3]
    }

    constructor(id: Int, title: String, publication: String, content: String?) : this() {
        this.id = id
        this.title = title
        this.date = publication
        this.content = content
    }

    constructor(id: Int, title: String, publication: Calendar, content: String?)
            : this(id, title, dateToString(publication), content)

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo
    var title: String? = null
    @ColumnInfo
    var shortDescription: String? = null
    @ColumnInfo
    var date: String? = null
    @ColumnInfo
    var content: String? = null

    fun getDateInLongFormat(): String? {
        return if (date == null) {
            null
        }
        else dateToCalendar(date!!).getPublicationDate()
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeStringArray(arrayOf(id.toString(), title, date, content))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsItem> {
        @SuppressLint("SimpleDateFormat")
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        override fun createFromParcel(parcel: Parcel): NewsItem {
            return NewsItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsItem?> {
            return arrayOfNulls(size)
        }

        fun dateToCalendar(date: String): Calendar {
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(date)
            return calendar
        }

        fun dateToString(date: Calendar): String {
            return dateFormat.format(date.time)
        }
    }
}