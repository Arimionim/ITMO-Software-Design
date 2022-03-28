import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.net.http.HttpConnectTimeoutException
import java.time.Instant

open class VKClient(private val token: String) {
    private val httpClient = HttpClient(CIO) {
        expectSuccess = false

        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }

        install(JsonFeature) {
            serializer = JacksonSerializer {
                propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
            }
        }
    }

    open suspend fun newsFeedRequest(
        q: String,
        count: Int,
        startTime: Long,
        endTime: Long
    ):  FeedResponse<JsonNode> {
        return request<Wrapper<FeedResponse<JsonNode>>>("newsfeed.search", mapOf(
            "q" to q,
            "count" to count,
            "start_time" to startTime,
            "end_time" to endTime,
            "v" to "5.131"
        )).response
    }

    private suspend inline fun <reified T> request(
        method: String,
        parameters: Map<String, Any>
    ): T {
        val response: HttpResponse = try {
            httpClient.request {
                url(
                    "https",
                    "api.vk.com",
                    443,
                    "/method/$method"
                )
                parameters.forEach(this::parameter)
                parameter("access_token", token)
            }
        } catch (exc: Exception) {
            when (exc) {
                is HttpConnectTimeoutException,
                is HttpRequestTimeoutException -> throw TimeoutException(exc)
                else -> throw RequestFailedException(exc)
            }
        }

        if (response.status != HttpStatusCode.OK) {
            throw BadStatusCodeException(response.status.value)
        }

        return response.receive()
    }

    suspend fun getStatsByHashtag(hashtag: String, hours: Int = 24, currentTime: Instant): List<Int> {
        return (0 until hours).map { n ->
            newsFeedRequest(
                hashtag,
                0,
                currentTime.minusSeconds(60L * 60 * (n + 1)).epochSecond,
                currentTime.minusSeconds(60L * 60 * n + 1).epochSecond
            ).totalCount
        }
    }
}