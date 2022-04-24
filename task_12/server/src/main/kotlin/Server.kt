import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            post("/add-stock") {
                val params = call.receiveParameters()

                val ticker = params["ticker"]
                    ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid ticker field")
                val companyName = params["company"]
                    ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid company field")
                val quantity = params["quantity"]?.toIntOrNull()
                    ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid quantity field")

                StockStorage.addStock(ticker, companyName, quantity)

                call.respond("""OK""")
            }

            get("/stock/{ticker}") {
                val ticker = call.parameters["ticker"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ticker field")

                val stock = StockStorage.getStock(ticker)
                    ?: return@get call.respond(HttpStatusCode.NotFound, "No such stock.")

                call.respond(
                    """
                    $$ticker: ${stock.companyName}
                    Available: ${stock.quantity}
                    Price per stock: ${stock.price}
                """.trimIndent()
                )
            }

            post("/stock/{ticker}/order") {
                val ticker = call.parameters["ticker"]
                    ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid ticker field")

                val params = call.receiveParameters()
                val quantity = params["quantity"]?.toIntOrNull()
                    ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid quantity field")

                try {
                    val price = StockStorage.changeQuantityAndGetPrice(ticker, quantity)
                    call.respond("OK PPU $price")
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Unexpected error")
                }
            }
        }
    }.start()

    while (true) {
        Thread.sleep(10000)
        runBlocking { StockStorage.emulateMarket() }
    }
}
