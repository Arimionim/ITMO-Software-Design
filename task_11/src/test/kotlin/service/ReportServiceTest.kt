package service

import core.BaseTest
import core.Faker
import core.TestApplication
import domain.Direction
import domain.event.IssueSubEvent
import domain.event.VisitEvent
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import kotlin.test.assertEquals

class ReportServiceTest : BaseTest() {
    private val reportService: ReportService by lazy { TestApplication.get() }

    @Test
    fun `get daily stats`() {
        val id = Faker.id()
        val instant = Faker.roundedInstant()
        val days = Faker.days()

        eventRepo.add(IssueSubEvent(id, instant, days))
        eventRepo.add(VisitEvent(id, Direction.IN, instant + Duration.ofMinutes(1)))
        eventRepo.add(VisitEvent(id, Direction.OUT, instant + Duration.ofMinutes(2)))
        eventRepo.add(VisitEvent(id, Direction.IN, instant + Duration.ofMinutes(3)))
        eventRepo.add(VisitEvent(id, Direction.OUT, instant + Duration.ofMinutes(4)))
        eventRepo.add(VisitEvent(id, Direction.IN, instant + Duration.ofMinutes(1441)))
        eventRepo.add(VisitEvent(id, Direction.OUT, instant + Duration.ofMinutes(1442)))

        val stats = reportService.daily(id)

        assertEquals(
            mapOf(
                LocalDate.ofInstant(instant, ZoneOffset.UTC) to 2,
                LocalDate.ofInstant(instant + Duration.ofDays(1), ZoneOffset.UTC) to 1
            ),
            stats
        )
    }

    @Test
    fun `get average count`() {
        val id1 = Faker.id()
        val id2 = Faker.id()
        val instant = Faker.roundedInstant()
        val days = Faker.days()

        eventRepo.add(IssueSubEvent(id1, instant, days))
        eventRepo.add(IssueSubEvent(id2, instant, days))
        eventRepo.add(VisitEvent(id1, Direction.IN, instant + Duration.ofMinutes(1)))
        eventRepo.add(VisitEvent(id1, Direction.OUT, instant + Duration.ofMinutes(2)))
        eventRepo.add(VisitEvent(id1, Direction.IN, instant + Duration.ofMinutes(3)))
        eventRepo.add(VisitEvent(id1, Direction.OUT, instant + Duration.ofMinutes(4)))
        eventRepo.add(VisitEvent(id2, Direction.IN, instant + Duration.ofMinutes(1441)))
        eventRepo.add(VisitEvent(id2, Direction.OUT, instant + Duration.ofMinutes(1442)))

        val average = reportService.averageCount()
        assertEquals(1.5, average)
    }

    @Test
    fun `get average count of empty set`() {
        val average = reportService.averageCount()
        assertEquals(0.0, average)
    }

    @Test
    fun `get average duration`() {
        val id = Faker.id()
        val instant = Faker.roundedInstant()
        val days = Faker.days()

        eventRepo.add(IssueSubEvent(id, instant, days))
        eventRepo.add(VisitEvent(id, Direction.IN, instant + Duration.ofSeconds(1)))
        eventRepo.add(VisitEvent(id, Direction.OUT, instant + Duration.ofSeconds(4)))
        eventRepo.add(VisitEvent(id, Direction.IN, instant + Duration.ofSeconds(10)))
        eventRepo.add(VisitEvent(id, Direction.OUT, instant + Duration.ofSeconds(12)))
        eventRepo.add(VisitEvent(id, Direction.IN, instant + Duration.ofSeconds(10000)))
        eventRepo.add(VisitEvent(id, Direction.OUT, instant + Duration.ofSeconds(10007)))

        val average = reportService.averageDuration()
        assertEquals(4.0, average)
    }

    @Test
    fun `get average duration of empty set`() {
        val average = reportService.averageDuration()
        assertEquals(0.0, average)
    }

    private fun Faker.roundedInstant(): Instant = instant().let {
        it - Duration.ofHours(it.atZone(ZoneOffset.UTC).hour.toLong())
    }
}
