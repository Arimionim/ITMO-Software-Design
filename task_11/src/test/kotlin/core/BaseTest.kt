package core

import org.junit.jupiter.api.BeforeEach
import repo.EventRepo

open class BaseTest {
    protected val eventRepo: EventRepo by lazy { TestApplication.get() }

    @BeforeEach
    fun clean() {
        eventRepo.clean()
    }
}
