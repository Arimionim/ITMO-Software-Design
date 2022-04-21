package http

import io.netty.buffer.ByteBuf
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import io.reactivex.netty.protocol.http.server.HttpServerResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import controllers.AddController
import controllers.ListController
import controllers.RegistrationController
import rx.Observable

class Handler : KoinComponent {
    private val addController by inject<AddController>()
    private val listController by inject<ListController>()
    private val sighupController by inject<RegistrationController>()

    fun handle(req: HttpServerRequest<ByteBuf>, res: HttpServerResponse<ByteBuf>): Observable<Void> {
        return when (req.decodedPath) {
            "/sighup" -> sighupController
            "/list" -> listController
            "/add" -> addController
            else -> {
                res.status = HttpResponseStatus.NOT_FOUND
                return res.writeString(Observable.just("404 :("))
            }
        }.handleSafe(req, res)
    }
}
