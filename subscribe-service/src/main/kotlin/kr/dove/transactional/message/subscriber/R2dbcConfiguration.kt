package kr.dove.transactional.message.subscriber

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory
import com.github.jasync.sql.db.mysql.util.URLParser
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
@ConfigurationProperties(prefix = "spring.r2dbc")
class R2dbcConfiguration : AbstractR2dbcConfiguration() {

    lateinit var url: String
    lateinit var username: String
    lateinit var password: String

    override fun connectionFactory(): ConnectionFactory = orderConnectionFactory()

    @Bean
    fun orderConnectionFactory(): ConnectionFactory = generateConnectionFactory(url, username, password)

    @Bean
    fun ddlInitializer(): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory())
        initializer.setDatabasePopulator(
            ResourceDatabasePopulator(
                ClassPathResource("ddl.sql")
            )
        )

        return initializer
    }

    private fun generateConnectionFactory(url: String, username: String, password: String): ConnectionFactory {
        val properties = URLParser.parseOrDie(url)
        return JasyncConnectionFactory(
            MySQLConnectionFactory(
                com.github.jasync.sql.db.Configuration(
                    username = username,
                    password = password,
                    host = properties.host,
                    port = properties.port,
                    database = properties.database,
                    charset = properties.charset,
                    ssl = properties.ssl
                ))
        )
    }
}