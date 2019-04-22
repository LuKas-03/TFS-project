package tfs.homeworks.project.network

import java.util.*

data class TinkoffNewsData(
    var resultCode: String,
    var payload: List<NewsSimpleData>,
    var trackingId: String
)
{
    data class NewsSimpleData (
        var id: Int,
        var name: String,
        var text: String,
        var publicationDate: PublicationDate,
        var bankInfoTypeId: Int
    )
    {
        data class PublicationDate(
            val milliseconds: Long
        )
    }
}