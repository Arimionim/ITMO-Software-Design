package service

import core.BaseTest
import core.Faker
import core.TestApplication
import domain.Direction
import domain.event.ExtendSubEvent
import domain.event.IssueSubEvent
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.Duration

class TurnstileServiceTest : BaseTest() {
    private val turnstileService: TurnstileService by lazy { TestApplication.get() }

    @Test
    fun `should allow`() {
        val id = Faker.id()
        val instant = Faker.instant()
        val days = Faker.days()

        eventRepo.add(IssueSubEvent(id, instant, days))

        assertDoesNotThrow {
            turnstileService.pass(id, Direction.IN, instant)
        }
    }

    @Test
    fun `should deny`() {
        val id = Faker.id()
        val instant = Faker.instant()
        val days = Faker.days()

        eventRepo.add(IssueSubEvent(id, instant, days))

        assertThrows<IllegalArgumentException> {
            turnstileService.pass(id, Direction.IN, instant + Duration.ofDays(days + 1))
        }
    }

    @Test
    fun `should allow after extend`() {
        val id = Faker.id()
        val instant = Faker.instant()
        val days = Faker.days()

        eventRepo.add(IssueSubEvent(id, instant, days))
        eventRepo.add(ExtendSubEvent(id, 2L))

        assertDoesNotThrow {
            turnstileService.pass(id, Direction.IN, instant + Duration.ofDays(days + 1))
        }
    }
}
