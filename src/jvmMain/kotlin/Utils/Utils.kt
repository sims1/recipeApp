package store

import java.time.Instant

fun withDurationPrinting(name: String, lambda: () -> Unit) {
    val startTime = Instant.now()
    lambda.invoke()
    val duration = Instant.now().toEpochMilli() - startTime.toEpochMilli()
    println("$name took $duration milli seconds")
}