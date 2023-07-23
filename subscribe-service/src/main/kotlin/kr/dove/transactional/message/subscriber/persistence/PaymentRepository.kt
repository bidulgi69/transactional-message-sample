package kr.dove.transactional.message.subscriber.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PaymentRepository : ReactiveCrudRepository<Payment, Long> {
}