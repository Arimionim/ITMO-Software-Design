package service

import com.mongodb.client.model.Filters.empty
import core.BaseTest
import core.Faker
import core.TestApplication
import domain.SubInfo
import domain.event.IssueSubEvent
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ManagerServiceTest : BaseTest() {
    private val managerService: ManagerService by lazy { TestApplication.get() }

    @Test
    fun `should issue subscription`() {
        val instant = Faker.instant()
        val days = Faker.days()

        val subId = managerService.issue(instant, days)

        val events = eventRepo.find(empty())

        assertEquals(listOf(IssueSubEvent(subId, instant, days)), events)
    }

    @Test
    fun `should get subscription`() {
        val id = Faker.id()
        val instant = Faker.instant()
        val days = Faker.days()

        eventRepo.add(IssueSubEvent(id, instant, days))

        val sub = managerService.get(id)

        assertEquals(SubInfo(id, instant, days), sub)
    }

    @Test
    fun `should prolong subscription`() {
        val instant = Faker.instant()
        val days = Faker.days()

        val subId = managerService.issue(instant, days)

        val extendDays = Faker.days()
        managerService.extend(subId, extendDays)

        val sub = managerService.get(subId)
        assertEquals(SubInfo(subId, instant, days + extendDays), sub)
    }

    @Test
    fun `should throw when no such subscription`() {
        assertThrows<IllegalArgumentException> {
            managerService.get(Faker.id())
        }
    }
}
