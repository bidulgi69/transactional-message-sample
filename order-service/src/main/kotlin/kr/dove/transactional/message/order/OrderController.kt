package kr.dove.transactional.message.order

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class OrderController(
    private val orderService: OrderService,
) {

    @PostMapping(
        value = ["/order"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveOrder(@RequestParam(name = "agency", required = false) agency: String): Mono<Void> = orderService.saveOrder(agency)

}