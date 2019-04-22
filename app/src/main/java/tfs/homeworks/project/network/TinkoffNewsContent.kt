package tfs.homeworks.project.network

data class TinkoffNewsContent(
    var payload: NewsData
)
{
    data class NewsData (
        var content: String
    )
}