package repo

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import domain.EventType
import domain.SubInfo
import domain.event.ExtendSubEvent
import domain.event.IssueSubEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Instant
import kotlin.math.abs
import kotlin.random.Random

class SubRepo : KoinComponent {
    private val eventRepo by inject<EventRepo>()

    fun issue(instant: Instant, validity: Long): Long {
        val subId = abs(Random.nextLong())
        eventRepo.add(IssueSubEvent(subId, instant, validity))
        return subId
    }

    fun extend(subId: Long, extraValidity: Long) {
        eventRepo.find(
            and(
                eq("type", EventType.ISSUE_SUB.toString()),
                eq("subId", subId)
            )
        ).firstOrNull()
            ?: throw IllegalArgumentException("No such sub.")

        eventRepo.add(ExtendSubEvent(subId, extraValidity))
    }

    fun get(subId: Long): SubInfo {
        val root = eventRepo.find(
            and(
                eq("type", EventType.ISSUE_SUB.toString()),
                eq("subId", subId)
            )
        ).firstOrNull() as? IssueSubEvent
            ?: throw IllegalArgumentException("No such sub.")

        val validity = eventRepo.find(
            and(
                eq("type", EventType.EXTEND_SUB.toString()),
                eq("subId", subId)
            )
        ).fold(root.validity) { oldValidity, event ->
            when (event) {
                is ExtendSubEvent -> oldValidity + event.extraValidity
                else -> oldValidity
            }
        }

        return SubInfo(subId, root.issueTimestamp, validity)
    }
}
