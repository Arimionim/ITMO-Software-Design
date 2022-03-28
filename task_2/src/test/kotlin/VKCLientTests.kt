import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.anyString
import org.mockito.Mockito.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.time.Instant
import kotlin.test.assertEquals

class VKCLientTests {
    private lateinit var vkClient: VKClient

    @BeforeEach
    fun prepareMock() {
        vkClient = mock(VKClient::class.java)
    }

    @Test
    fun shouldReturnCorrectValue() = runBlocking {
        `when`(vkClient.newsFeedRequest(
            anyString(), anyInt(), anyLong(), anyLong()
        )).thenReturn(FeedResponse(listOf(), 0, 123, null))

        assertEquals(
            listOf(123),
            vkClient.getStatsByHashtag("#test", 1, Instant.now())
        )
    }

    @Test
    fun shouldSortValues() = runBlocking {
        `when`(vkClient.newsFeedRequest(
            anyString(), anyInt(),
            eq(Instant.now().minusSeconds(60L * 60).epochSecond),
            anyLong()
        )).thenReturn(FeedResponse(listOf(), 0, 123, null))

        `when`(vkClient.newsFeedRequest(
            anyString(), anyInt(),
            eq(Instant.now().minusSeconds(60L * 60 * 2).epochSecond),
            anyLong()
        )).thenReturn(FeedResponse(listOf(), 0, 456, null))


        assertEquals(
            listOf(123, 456),
            vkClient.getStatsByHashtag("#test", 2, Instant.now())
        )
    }

    @Test
    fun shouldCallClientNTimes() {
        runBlocking {
            `when`(vkClient.newsFeedRequest(
                anyString(), anyInt(), anyLong(), anyLong()
            )).thenReturn(FeedResponse(listOf(), 0, 0, null))

            val startTime = Instant.now()
            vkClient.getStatsByHashtag("#test", 2, Instant.now())

            verify(vkClient, times(1)).newsFeedRequest(
                "#test", 0,
                startTime.minusSeconds(60L * 60).epochSecond,
                startTime.minusSeconds(1).epochSecond
            )

            verify(vkClient, times(1)).newsFeedRequest(
                "#test", 0,
                startTime.minusSeconds(60L * 60 * 2).epochSecond,
                startTime.minusSeconds(60L * 60 + 1).epochSecond
            )

            verify(vkClient, times(2)).newsFeedRequest(
                anyString(), anyInt(), anyLong(), anyLong()
            )
        }
    }
}
