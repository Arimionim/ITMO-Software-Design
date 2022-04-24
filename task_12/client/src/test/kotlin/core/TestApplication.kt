import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import appModule
import provider.ServerProvider

object TestApplication {
    val app = startKoin {
        printLogger()
        modules(
            module {
                single { TestServerProvider() } bind ServerProvider::class
            },
            appModule
        )
    }.koin

    inline fun <reified T : Any> get() = app.get<T>()
}
