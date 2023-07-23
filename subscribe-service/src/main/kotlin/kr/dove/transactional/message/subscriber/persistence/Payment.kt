package kr.dove.transactional.message.subscriber.persistence

import enumeration.PaymentAgency
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(value = "payments")
data class Payment(
    @Id val paymentId: Long? = null,
    val transactionId: String,
    val orderId: Long,
    val agency: PaymentAgency,
    val success: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)