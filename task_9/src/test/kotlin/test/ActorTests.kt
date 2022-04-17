package test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import engines.EngineType
import utils.getResults
import stub.OneshotStubServer
import stub.SleepyStubServer
import utils.withSearchMocks
import kotlin.test.assertEquals

@DisplayName("Test actor search system")
class ActorTests {
    @Test
    fun `all servers working`() = withSearchMocks(
        OneshotStubServer(8000),
        OneshotStubServer(8001),
        OneshotStubServer(8002)
    ) {
        val results = getResults("test")

        assertEquals(15, results.size)

        val engines = mutableMapOf<EngineType, Int>()

        results.forEach {
            engines.compute(it.type) { _, old -> (old ?: 0) + 1 }
            assertEquals("http://google.com", it.link)
        }

        EngineType.values().forEach { it ->
            assertEquals(5, engines[it])
        }
    }

    @Test
    fun `one server times out`() = withSearchMocks(
        OneshotStubServer(8000),
        OneshotStubServer(8001),
        SleepyStubServer(8002)
    ) {
        val results = getResults("test")

        assertEquals(10, results.size)

        val engines = mutableMapOf<EngineType, Int>()

        results.forEach {
            engines.compute(it.type) { _, old -> (old ?: 0) + 1 }
            assertEquals("http://google.com", it.link)
        }

        assertEquals(5, engines[EngineType.BING])
        assertEquals(5, engines[EngineType.GOOGLE])
        assertEquals(null, engines[EngineType.YANDEX])
    }
}
