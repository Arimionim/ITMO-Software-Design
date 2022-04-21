package domain.event

import domain.Direction
import domain.EventType
import java.time.Instant

data class VisitEvent(
    val subId: Long,
    val direction: Direction,
    val timestamp: Instant,
) : Event(EventType.VISIT_EVENT)
