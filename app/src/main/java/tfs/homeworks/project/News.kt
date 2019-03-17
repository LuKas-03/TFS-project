package tfs.homeworks.project

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class News : Parcelable {

    constructor(parcel: Parcel) {
        val data = arrayOfNulls<String>(4)
        parcel.readStringArray(data)
        title = data[0]
        shortDescription = data[1]
        date = if (data[2] != null) toCalendar(data[2]!!) else null
        content = data[3]
    }

    constructor(title: String, shortDescription: String, publication: Calendar, content: String?) {
        this.title = title
        this.shortDescription = shortDescription
        this.date = publication
        this.content = content
    }

    var title: String?
    var shortDescription: String?
    var date: Calendar?
    var content: String?




    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeStringArray(arrayOf(title, shortDescription, dateFormat.format(date?.time), content))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<News> {
        @SuppressLint("SimpleDateFormat")
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        override fun createFromParcel(parcel: Parcel): News {
            return News(parcel)
        }

        override fun newArray(size: Int): Array<News?> {
            return arrayOfNulls(size)
        }

        fun toCalendar(date: String): Calendar {
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(date)
            return calendar
        }

        fun toString(date: Calendar): String {
            return dateFormat.format(date.time)
        }
    }
}