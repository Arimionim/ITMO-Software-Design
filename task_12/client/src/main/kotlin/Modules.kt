import org.koin.dsl.module

val appModule = module {
    single { StockClient() }
    single { Broker() }
}
