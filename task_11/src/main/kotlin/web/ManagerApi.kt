package web

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import service.ManagerService
import java.time.Instant


class ManagerApi : KoinComponent {
    private val managerService by inject<ManagerService>()

    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/sub/{subId}") {
                val subId = call.longParameter("subId") ?: return@get

                val sub = try {
                    managerService.get(subId)
                } catch (ignored: IllegalArgumentException) {
                    print(ignored)
                    return@get call.respond(HttpStatusCode.NotFound, "No such sub")
                }

                call.respond(
                    """
                    Subscription: ${sub.subId}
                    Issued: ${sub.issueTimestamp.toHttpDateString()}
                    Valid for ${sub.validity} days
                    """.trimIndent()
                )
            }

            get("/issue/{days}") {
                val days = call.longParameter("days") ?: return@get

                val subId = managerService.issue(Instant.now(), days)

                call.respondRedirect("/sub/${subId}", permanent = false)
            }

            get("/sub/{subId}/prolong/{days}") {
                val subId = call.longParameter("subId") ?: return@get
                val days = call.longParameter("days") ?: return@get

                try {
                    managerService.extend(subId, days)
                } catch (ignored: IllegalArgumentException) {
                    return@get call.respond(HttpStatusCode.NotFound, "No such sub")
                }

                call.respondRedirect("/sub/${subId}", permanent = false)
            }
        }
    }
}

suspend fun ApplicationCall.longParameter(name: String): Long? {
    val value = parameters[name]?.toLongOrNull()

    if (value == null) {
        respond(HttpStatusCode.BadRequest, "Expected long parameter $name")
    }

    return value
}
