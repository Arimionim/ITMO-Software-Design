package application.profiled

import kotlin.random.Random

fun randomJob() {
    randomJob(0, 50)
}

fun randomJob(secMin: Long, secMax: Long) {
    Thread.sleep(Random.nextLong(secMin, secMax))
}