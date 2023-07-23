plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":api"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// https://mvnrepository.com/artifact/io.debezium/debezium-api
	implementation("io.debezium:debezium-api:${Version.debeziumVersion}")
	// https://mvnrepository.com/artifact/io.debezium/debezium-embedded
	implementation("io.debezium:debezium-embedded:${Version.debeziumVersion}")
	// https://mvnrepository.com/artifact/io.debezium/debezium-connector-mysql
	implementation("io.debezium:debezium-connector-mysql:${Version.debeziumVersion}")


	implementation("org.springframework.kafka:spring-kafka")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.getByName<Jar>("jar") {
	enabled = false
}