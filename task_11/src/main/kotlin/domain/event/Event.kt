package domain.event

import domain.EventType
import org.litote.kmongo.Data

@Data
abstract class Event(
    val type: EventType
)
