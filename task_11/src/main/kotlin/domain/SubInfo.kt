package domain

import java.time.Instant

data class SubInfo(
    val subId: Long,
    val issueTimestamp: Instant,
    val validity: Long
)
