package service

import domain.Direction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repo.SubRepo
import repo.VisitRepo
import java.time.Duration
import java.time.Instant

class TurnstileService : KoinComponent {
    private val subRepo by inject<SubRepo>()
    private val visitRepo by inject<VisitRepo>()

    fun pass(subId: Long, direction: Direction, timestamp: Instant) {
        val sub = subRepo.get(subId)
        if (sub.issueTimestamp + Duration.ofDays(sub.validity) < timestamp) {
            throw IllegalArgumentException("Subscription has expired")
        }

        visitRepo.mark(subId, direction, timestamp)
    }
}
