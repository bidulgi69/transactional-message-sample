package kr.dove.transactional.message.tailing

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class DebeziumConnectorConfiguration(
    @Value("\${debezium.mysql.hostname}") private val hostname: String,
    @Value("\${debezium.mysql.port}") private val port: String,
    @Value("\${debezium.mysql.username}") private val username: String,
    @Value("\${debezium.mysql.password}") private val password: String,
    @Value("\${debezium.mysql.database}") private val database: String,
    @Value("\${spring.profiles.active:default}") private val profile: String,
) {

    @Bean
    fun sourceConnector(): io.debezium.config.Configuration {
        val offsetStorage = File.createTempFile("offsets_", ".dat")
        val historyStorage = File.createTempFile("history_", ".dat")

        return io.debezium.config.Configuration
            .create()
            .with("name", "mysql-connector-engine")
            .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
            .with("offset.storage.file.filename", offsetStorage.absolutePath)
            .with("offset.flush.interval.ms", "60000")
            .with("database.hostname", hostname)
            .with("database.port", port.toInt())
            .with("database.user", username)
            .with("database.password", password)
            .with("database.server.id", 85744)
            .with("database.dbname", database)
            .with("database.include.list", database)
            .with("table.include.list", "$database.outbox")
            .with("topic.prefix", "connector")
            .with("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory")
            .with("schema.history.internal.file.filename", historyStorage.absolutePath)
            .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
            .with("include.schema.changes", "true")
            .with("database.allowPublicKeyRetrieval", "true")
            .build()
    }
}