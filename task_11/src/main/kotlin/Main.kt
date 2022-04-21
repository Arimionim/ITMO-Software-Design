import org.koin.core.context.startKoin
import web.ManagerApi

fun main() {
    val api = startKoin {
        printLogger()
        modules(serviceModule)
        modules(webModule)
    }.koin.get<ManagerApi>()

    api.server.start(wait = true)
}
