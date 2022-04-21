package domain.event

import domain.EventType

data class ExtendSubEvent(
    val subId: Long,
    val extraValidity: Long
) : Event(EventType.EXTEND_SUB)
