package outbox

import java.time.LocalDateTime

data class Outbox<out T>(
    val transactionId: String,
    val topic: String,
    val partitionKey: String,
    val payload: T,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)