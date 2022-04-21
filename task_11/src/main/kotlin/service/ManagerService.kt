package service

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repo.SubRepo
import java.time.Instant

class ManagerService : KoinComponent {
    private val subRepo by inject<SubRepo>()

    fun issue(instant: Instant, validity: Long): Long = subRepo.issue(instant, validity)

    fun extend(subId: Long, extraValidity: Long) = subRepo.extend(subId, extraValidity)

    fun get(subId: Long) = subRepo.get(subId)
}
