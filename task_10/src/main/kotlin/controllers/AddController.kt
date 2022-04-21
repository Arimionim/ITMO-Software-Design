package controllers

import io.netty.buffer.ByteBuf
import io.netty.handler.codec.http.HttpResponseStatus
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import entity.Currency
import entity.Item
import service.ItemService
import rx.Observable
import java.lang.IllegalArgumentException

class AddController : Controller(), KoinComponent {
    private val itemService by inject<ItemService>()

    override fun handle(req: HttpServerRequest<ByteBuf>): Observable<String> {
        val name = req.queryParam("name")

        val price = req.queryParam("price").toDoubleOrNull()
            ?: throw HttpError(HttpResponseStatus.BAD_REQUEST, "Invalid parameter 'price'")

        val currency = try {
            req.queryParam("currency").let(Currency::valueOf)
        } catch (e: IllegalArgumentException) {
            throw HttpError(HttpResponseStatus.BAD_REQUEST, "Invalid parameter 'currency'")
        }

        return itemService.add(Item(name, price, currency))
            .map { "Success" }
    }
}
