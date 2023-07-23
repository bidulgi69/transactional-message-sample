package kr.dove.transactional.message.subscriber

import kr.dove.transactional.message.subscriber.persistence.Payment
import kr.dove.transactional.message.subscriber.persistence.PaymentRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class PaymentController(
    private val paymentRepository: PaymentRepository,
){

    @GetMapping(
        value = ["/payments"],
        produces = [MediaType.APPLICATION_NDJSON_VALUE]
    )
    fun getAll(): Flux<Payment> {
        return paymentRepository.findAll()
    }
}