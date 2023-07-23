import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Version.springBootVersion
    id("io.spring.dependency-management") version Version.dependencyManagementVersion
    kotlin("jvm") version Version.kotlinPluginVersion apply false
    kotlin("plugin.spring") version Version.kotlinPluginVersion apply false
}

allprojects {
    group = "kr.dove.transactional.message"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

subprojects {

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

}