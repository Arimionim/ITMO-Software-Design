package messages

import engines.EngineType

data class SearchResult(
    val type: EngineType,
    val link: String
)
