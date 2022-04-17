package engines

import io.ktor.client.request.*

class GoogleEngineActor : BaseEngineActor(EngineType.GOOGLE) {
    override suspend fun findBestResults(query: String): List<String> {
        return client.get {
            url("http://127.0.0.1:8001/search")
            parameter("query", query)
        }
    }
}
