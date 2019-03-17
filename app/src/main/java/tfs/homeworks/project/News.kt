package tfs.homeworks.project

import android.os.Parcel
import android.os.Parcelable

class News : Parcelable {

    constructor(parcel: Parcel) {
        val data = arrayOfNulls<String>(4)
        parcel.readStringArray(data)
        title = data[0]
        shortDescription = data[1]
        date = data[2]
        content = data[3]
    }

    constructor(title: String?, shortDescription: String?, publication: String?, content: String?) {
        this.title = title
        this.shortDescription = shortDescription
        this.date = publication
        this.content = content
    }

    var title: String? = null
    var shortDescription: String? = null
    var date: String? = null
    var content: String? = null


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeStringArray(arrayOf(title, shortDescription, date, content))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<News> {
        override fun createFromParcel(parcel: Parcel): News {
            return News(parcel)
        }

        override fun newArray(size: Int): Array<News?> {
            return arrayOfNulls(size)
        }
    }
}