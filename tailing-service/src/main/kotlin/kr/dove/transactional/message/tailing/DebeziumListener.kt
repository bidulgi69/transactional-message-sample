package kr.dove.transactional.message.tailing

import io.debezium.config.Configuration
import io.debezium.data.Envelope.FieldName
import io.debezium.data.Envelope.Operation
import io.debezium.embedded.Connect
import io.debezium.engine.DebeziumEngine
import io.debezium.engine.RecordChangeEvent
import io.debezium.engine.format.ChangeEventFormat
import org.apache.kafka.connect.data.Struct
import org.apache.kafka.connect.errors.DataException
import org.apache.kafka.connect.source.SourceRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class DebeziumListener(
    sourceConnector: Configuration,
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) : InitializingBean, DisposableBean {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val executor = Executors.newSingleThreadExecutor()
    private val debeziumEngine: DebeziumEngine<RecordChangeEvent<SourceRecord>> = DebeziumEngine
        .create(ChangeEventFormat.of(Connect::class.java))
        .using(sourceConnector.asProperties())
        .notifying { event ->
            handleChangeEvent(event)
        }
        .build()

    private fun handleChangeEvent(sourceRecordChangeEvent: RecordChangeEvent<SourceRecord>) {
        val sourceRecord: SourceRecord = sourceRecordChangeEvent.record()
        logger.info("Key = ${sourceRecord.key()}, value = ${sourceRecord.value()}")

        try {
            with(sourceRecord.value() as Struct) {
                val code: String = try {
                    get(FieldName.OPERATION) as String
                } catch (e: DataException) {
                    //  when operation is not present.
                    return
                }

                val operation = Operation.forCode(code) ?. let { op ->
                    when (op) {
                        Operation.CREATE -> FieldName.AFTER
                        Operation.DELETE -> FieldName.BEFORE
                        Operation.READ -> return
                        else -> throw UnsupportedOperationException("Unsupported database operations :: $op")
                    }
                }
                //  get data from binlog
                val struct = get(operation) as Struct
                //  parse
                val transactionId = struct.get("transaction_id") as String
                val topic = struct.get("topic") as String
                val payload = struct.get("payload") as String
                val partitionKey = struct.get("partition_key") as String
                logger.info("Message: Id($transactionId) to topic $topic[#$partitionKey] with message:: $payload")
                //  send
                kafkaTemplate.send(
                    topic,
                    partitionKey,
                    payload
                )
            }
        } catch (e: RuntimeException) {
            //  throw error
            e.printStackTrace()
        }
    }

    override fun afterPropertiesSet() {
        executor.execute(debeziumEngine)
    }

    override fun destroy() {
        debeziumEngine.close()
    }
}