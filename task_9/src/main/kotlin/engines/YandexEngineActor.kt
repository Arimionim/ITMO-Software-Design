package engines

import io.ktor.client.request.*

class YandexEngineActor : BaseEngineActor(EngineType.YANDEX) {
    override suspend fun findBestResults(query: String): List<String> {
        return client.get {
            url("http://127.0.0.1:8002/search")
            parameter("query", query)
        }
    }
}
