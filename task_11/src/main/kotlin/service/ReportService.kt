package service

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import domain.Direction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repo.VisitRepo
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import kotlin.math.max

class ReportService : KoinComponent {
    private val visitRepo by inject<VisitRepo>()

    fun daily(subId: Long): Map<LocalDate, Int> =
        visitRepo
            .filter(
                and(
                    eq("subId", subId),
                    eq("direction", Direction.IN.toString())
                )
            )
            .groupBy { LocalDate.ofInstant(it.timestamp, ZoneOffset.UTC) }
            .mapValues { it.value.size }

    fun averageCount(): Double =
        visitRepo
            .filter(eq("direction", Direction.IN.toString()))
            .groupBy { it.subId }
            .map { it.value.size }
            .let { it.sum().toDouble() / max(it.size, 1) }

    fun averageDuration(): Double =
        visitRepo
            .all()
            .sortedBy { it.timestamp }
            .fold(DurationHolder(0, 0, mapOf())) { holder, visit ->
                when (visit.direction) {
                    Direction.IN -> DurationHolder(
                        holder.visits,
                        holder.duration,
                        holder.enterTime.plus(visit.subId to visit.timestamp)
                    )
                    Direction.OUT -> {
                        val duration = Duration.between(
                            holder.enterTime
                                .getOrElse(visit.subId) { throw IllegalArgumentException("Exit-only member") },
                            visit.timestamp
                        ).toSeconds()
                        DurationHolder(
                            holder.visits + 1,
                            holder.duration + duration,
                            holder.enterTime.minus(visit.subId)
                        )
                    }
                }
            }
            .let {
                it.duration.toDouble() / max(it.visits, 1)
            }

    private data class DurationHolder(
        val visits: Long,
        val duration: Long,
        val enterTime: Map<Long, Instant>
    )
}
