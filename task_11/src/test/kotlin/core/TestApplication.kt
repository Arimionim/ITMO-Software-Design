package core

import org.koin.core.context.startKoin
import repo.EventRepo
import serviceModule

object TestApplication {
    val app = startKoin {
        modules(serviceModule)
    }.koin

    inline fun <reified T : Any> get() = app.get<T>()
}
