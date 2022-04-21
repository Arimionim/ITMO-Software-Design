package domain.event

import domain.EventType
import java.time.Instant

data class IssueSubEvent(
    val subId: Long,
    val issueTimestamp: Instant,
    val validity: Long
) : Event(EventType.ISSUE_SUB)
