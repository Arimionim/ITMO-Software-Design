package messages

// To avoid type erasure issues.
data class SearchResultResponse(
    val data: List<SearchResult>
)
