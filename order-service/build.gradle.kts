plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":api"))

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("com.github.jasync-sql:jasync-r2dbc-mysql:${Version.jasyncVersion}")
	implementation("com.google.code.gson:gson:${Version.gsonVersion}")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.getByName<Jar>("jar") {
	enabled = false
}