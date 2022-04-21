package service

import com.mongodb.rx.client.Success
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import dao.UserDao
import entity.User
import rx.Observable

class UserService : KoinComponent {
    private val dao by inject<UserDao>()

    fun add(user: User) : Observable<Success> {
        return dao.add(user)
    }

    fun one(name: String): Observable<User> {
        return dao.one(name).single()
    }
}
