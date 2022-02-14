import kotlinx.coroutines.runBlocking
import java.time.Instant
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val token = ""
    val client = VKClient(token)
    if (args.size != 2) {
        throw IllegalArgumentException("Usage: hashtag hours")
    }

    val hashtag = args[0]
    val hours = args[1].toInt()
    val currentTime = Instant.now()

    val postsPerHour = try {
        runBlocking {
            client.getStatsByHashtag(
                hashtag,
                hours,
                currentTime
            )
        }
    } catch (e: Exception) {
        System.err.println("Failed $e")
        exitProcess(1)
    }

    println("Posts with tag $hashtag per hour:")
    for (n in postsPerHour.indices) {
        println("Since ${currentTime.minusSeconds(60L * 60 * (n + 1))}:\t${postsPerHour[n]}")
    }
}