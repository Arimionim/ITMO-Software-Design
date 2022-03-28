import clock.Clock
import clock.InstantClock
import java.util.LinkedList
import java.util.Queue
import kotlin.collections.HashMap

class HashEventsStatistic(private val clock: Clock = InstantClock()) : EventsStatistic {
    override fun incEvent(name: String) {
        if (!events.containsKey(name)) {
            events[name] = LinkedList()
        }
        events[name]?.add(System.currentTimeMillis())
    }

    override fun getEventStatisticByName(name: String): Int {
        trim(name)
        return (events[name]?.size ?: 0) / 60
    }

    override fun getAllEventStatistic(): Map<String, Int> {
        val res = HashMap<String, Int>()
        for (event in events) {
            res[event.key] = getEventStatisticByName(event.key)
        }

        return res
    }

    override fun printStatistic() {
        val res = getAllEventStatistic()

        for (event in res) {
            println("Event: " + event.key + ", rpm is " + event.value)
        }
    }

    private fun trim(name: String) {
        val currentTimestamp = clock.now()

        while (events[name]!!.isNotEmpty() && events[name]!!.peek() + hourms < currentTimestamp.epochSecond) {
            events[name]!!.remove()
        }
    }

    private val hourms = 1000 * 60 * 60
    private var events = HashMap<String, Queue<Long>>()
}