import org.junit.jupiter.api.Test
import java.time.Instant

class Tests {
    val clock = TestClock()
    @Test
    fun noEvents() {
        val res = HashEventsStatistic(clock)
        assert(res.getAllEventStatistic().isEmpty())
    }

    @Test
    fun forgetOldEvents() {
        val stats = HashEventsStatistic(clock)

        clock.setTime(Instant.parse("2022-03-28T00:00:00Z"))
        stats.incEvent("a")

        clock.setTime(Instant.parse("2022-03-28T01:00:00Z"))
        stats.incEvent("a")

        clock.setTime(Instant.parse("2022-03-28T01:30:00Z"))
        stats.incEvent("a")

        clock.setTime(Instant.parse("2022-03-28T01:35:00Z"))
        stats.incEvent("a")

        clock.setTime(Instant.parse("2022-03-28T01:45:00Z"))

        assert(stats.getEventStatisticByName("a") == 3/60)
    }

    @Test
    fun severalEvents() {
        val stats = HashEventsStatistic(clock)

        clock.setTime(Instant.parse("2022-03-28T00:00:00Z"))
        stats.incEvent("a")

        clock.setTime(Instant.parse("2022-03-28T01:00:00Z"))
        stats.incEvent("a")

        clock.setTime(Instant.parse("2022-03-28T01:30:00Z"))
        stats.incEvent("a")

        clock.setTime(Instant.parse("2022-03-28T01:35:00Z"))
        stats.incEvent("a")

        clock.setTime(Instant.parse("2022-03-28T00:00:00Z"))
        stats.incEvent("b")

        clock.setTime(Instant.parse("2022-03-28T01:00:00Z"))
        stats.incEvent("b")

        clock.setTime(Instant.parse("2022-03-28T01:30:00Z"))
        stats.incEvent("b")

        clock.setTime(Instant.parse("2022-03-28T01:35:00Z"))
        stats.incEvent("b")

        clock.setTime(Instant.parse("2022-03-28T01:35:01Z"))
        stats.incEvent("b")

        clock.setTime(Instant.parse("2022-03-28T01:45:00Z"))

        assert(stats.getAllEventStatistic() == mapOf("a" to 4/60, "b" to 5/60))
    }
}