package kr.dove.transactional.message.order.persistence

import enumeration.PaymentAgency
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(value = "orders")
data class Order(
    @Id val orderId: Long? = null,
    val paidAt: LocalDateTime,
    val agency: PaymentAgency,
    val transactionId: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)
