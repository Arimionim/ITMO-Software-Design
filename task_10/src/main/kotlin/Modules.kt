import com.mongodb.rx.client.MongoClients
import org.koin.dsl.module
import controllers.AddController
import controllers.ListController
import controllers.RegistrationController
import dao.ItemDao
import dao.UserDao
import http.Handler
import service.ItemService
import service.UserService

val mainModule = module {
    single { MongoClients.create("mongodb://localhost:27017") }
    single { AddController() }
    single { ListController() }
    single { RegistrationController() }
    single { ItemDao() }
    single { UserDao() }
    single { Handler() }
    single { ItemService() }
    single { UserService() }
}
