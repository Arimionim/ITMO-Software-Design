import com.mongodb.client.MongoClients
import org.koin.dsl.module
import repo.EventRepo
import repo.SubRepo
import repo.VisitRepo
import service.ManagerService
import service.ReportService
import service.TurnstileService
import web.ManagerApi

val serviceModule = module {
    single { MongoClients.create("mongodb://localhost:27017").getDatabase("db") }
    single { EventRepo() }
    single { SubRepo() }
    single { VisitRepo() }
    single { ManagerService() }
    single { ReportService() }
    single { TurnstileService() }
}

val webModule = module {
    single { ManagerApi() }
}
