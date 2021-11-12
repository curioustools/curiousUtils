package work.curioustools.third_party_exoplayer

import java.util.concurrent.TimeUnit

object ExoConstants {
    const val ONE_KB = 1024
    const val ONE_MB = 1024 * ONE_KB
    const val ONE_GB = 1024 * ONE_MB
    const val SIXTY_FOUR_KB = 64* ONE_KB
    const val ZERO = 0

    val FIFTY_SECONDS = TimeUnit.SECONDS.toMillis(50).toInt()
    val TWO_MINS = TimeUnit.MINUTES.toMillis(2).toInt()
    val TEN_MINS = TimeUnit.MINUTES.toMillis(10).toInt()
    val TEN_SECONDS = TimeUnit.SECONDS.toMillis(10).toInt()
    val ONE_SECOND = TimeUnit.SECONDS.toMillis(1).toInt()

}