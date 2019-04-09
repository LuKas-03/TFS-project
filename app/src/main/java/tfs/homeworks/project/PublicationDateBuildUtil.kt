package tfs.homeworks.project

import android.content.res.Resources
import java.util.*

class PublicationDateBuildUtil {

    companion object {
        private val systemResources = Resources.getSystem()

        fun Calendar.getPublicationDate(): String {
            val now = Calendar.getInstance()
            if (now[Calendar.YEAR] == this[Calendar.YEAR] && now[Calendar.MONTH] == this[Calendar.MONTH]){
                if (now[Calendar.DAY_OF_MONTH] == this[Calendar.DAY_OF_MONTH]) {
                    return systemResources.getString(R.string.today_ru)
                }
                now.add(Calendar.DATE, -1)
                if (now[Calendar.DATE] == this[Calendar.DATE]) {
                    return systemResources.getString(R.string.yesterday_ru)
                }
            }
            return "${this[Calendar.DATE]} ${monthTranslate[this[Calendar.MONTH]]}, ${this[Calendar.YEAR]}"
        }

        private val monthTranslate: Map<Int, String> = mapOf(
            Calendar.JANUARY to "января",
            Calendar.FEBRUARY to "февраля",
            Calendar.MARCH to "марта",
            Calendar.APRIL to "апреля",
            Calendar.MAY to "мая",
            Calendar.JUNE to "июня",
            Calendar.JULY to "июля",
            Calendar.AUGUST to "августа",
            Calendar.SEPTEMBER to "сентября",
            Calendar.OCTOBER to "октября",
            Calendar.NOVEMBER to "ноября",
            Calendar.DECEMBER to "декабря"
        )
    }
}