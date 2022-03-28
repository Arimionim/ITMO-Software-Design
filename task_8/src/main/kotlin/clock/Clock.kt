package clock

import java.time.Instant

interface Clock {
    fun now() : Instant
}

class InstantClock : Clock {
    override fun now() : Instant = Instant.now()
}