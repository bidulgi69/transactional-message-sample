package kr.dove.transactional.message.order

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class OrderServiceApplication {

	@Bean
	fun gson(): Gson = GsonBuilder()
		.setLenient()
		.create()
}

fun main(args: Array<String>) {
	runApplication<OrderServiceApplication>(*args)
}
