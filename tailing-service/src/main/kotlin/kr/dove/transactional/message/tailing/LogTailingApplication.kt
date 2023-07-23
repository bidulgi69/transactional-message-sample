package kr.dove.transactional.message.tailing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LogTailingApplication

fun main(args: Array<String>) {
	runApplication<LogTailingApplication>(*args)
}
