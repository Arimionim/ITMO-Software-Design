package controllers

import io.netty.buffer.ByteBuf
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import service.ItemService
import service.UserService
import rx.Observable

class ListController : Controller(), KoinComponent {
    private val itemService by inject<ItemService>()
    private val userService by inject<UserService>()

    override fun handle(req: HttpServerRequest<ByteBuf>): Observable<String> {
        val username = req.queryParameters.getOrDefault("username", null)
            ?.getOrNull(0)
            ?: throw HttpError(HttpResponseStatus.BAD_REQUEST, "Missing parameter 'username'")

        return userService.one(username)
            .singleOrDefault(null)
            .flatMap { user ->
                Observable.merge(
                    Observable.just("All prices in ${user.currency}:\n"),
                    itemService.all(user.currency)
                        .map { item ->
                            "${item.name}: ${item.price}\n"
                        }
                )
            }
            .onErrorReturn { when (it) {
                is NoSuchElementException -> throw HttpError(HttpResponseStatus.FORBIDDEN, "No such user.")
                else -> throw it
            } }
    }
}
