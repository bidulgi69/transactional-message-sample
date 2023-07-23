package kr.dove.transactional.message.subscriber

import com.google.gson.Gson
import kr.dove.transactional.message.subscriber.persistence.Payment
import kr.dove.transactional.message.subscriber.persistence.PaymentRepository
import messages.order.OrderMessagePayload
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class KafkaConsumerConfiguration(
    private val gson: Gson,
    private val paymentRepository: PaymentRepository,
) {

    @Value("\${kafka.bootstrapAddress}")
    lateinit var bootstrapAddress: String

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val config = mutableMapOf<String, Any>()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        config[ConsumerConfig.GROUP_ID_CONFIG] = "consumer-group"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @KafkaListener(
        topics = ["order"],
        groupId = "consumer-group"
    )
    fun consume(message: String) {
        logger.info("Consumed message: $message")
        val payload: OrderMessagePayload = gson.fromJson(message, OrderMessagePayload::class.java)

        paymentRepository.save(
            Payment(
                transactionId = payload.transactionId,
                orderId = payload.orderId,
                agency = payload.agency,
            )
        ).subscribe()   //  execute
    }
}