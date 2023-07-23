package messages.order

import enumeration.PaymentAgency

data class OrderMessagePayload(
    val orderId: Long,
    val agency: PaymentAgency,
    val transactionId: String,
    //  ... 그 외 다른 필요한 정보들
)