data class Wrapper<T>(val response: T)

data class FeedResponse<T>(
    val items: List<T>,
    val count: Int,
    val totalCount: Int,
    val suggestedQueries: Any?
)
