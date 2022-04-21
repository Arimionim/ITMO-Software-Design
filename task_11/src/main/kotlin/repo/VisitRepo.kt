package repo

import com.mongodb.client.model.Filters.*
import domain.Direction
import domain.EventType
import domain.event.VisitEvent
import org.bson.conversions.Bson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Instant

class VisitRepo : KoinComponent {
    private val eventRepo by inject<EventRepo>()

    fun mark(subId: Long, direction: Direction, instant: Instant) {
        eventRepo.add(VisitEvent(subId, direction, instant))
    }

    fun filter(filter: Bson): List<VisitEvent> {
        return eventRepo
            .find(
                and(
                    filter,
                    eq("type", EventType.VISIT_EVENT.toString())
                )
            )
            .toList()
            .filterIsInstance(VisitEvent::class.java)
    }

    fun all() = filter(empty())
}
