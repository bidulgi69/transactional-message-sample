package kr.dove.transactional.message.order

import com.google.gson.Gson
import enumeration.PaymentAgency
import kr.dove.transactional.message.order.persistence.Order
import messages.order.OrderMessagePayload
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import outbox.Outbox
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@Service
class OrderService(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val gson: Gson,
) {

    @Transactional
    fun saveOrder(agency: String): Mono<Void> {
        val paymentAgency = PaymentAgency.of(agency)
        val transactionId = UUID.randomUUID().toString()
        return r2dbcEntityTemplate.insert(
            Order(
                paidAt = LocalDateTime.now(),
                agency = paymentAgency,
                transactionId = transactionId
            )
        )
            .flatMap { order ->
                //  order 테이블에 성공적으로 저장된 이후, 메세지를 db 에 저장한다.
                r2dbcEntityTemplate.insert(
                    Outbox(
                        transactionId = transactionId,
                        topic = "order",
                        partitionKey = "${order.orderId!!}",
                        payload = gson.toJson(
                            OrderMessagePayload(
                                orderId = order.orderId,
                                agency = paymentAgency,
                                transactionId = transactionId
                            )
                        )
                    )
                )
            }
            .then()
    }
}