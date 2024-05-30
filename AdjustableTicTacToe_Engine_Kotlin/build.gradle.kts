plugins {
    kotlin("jvm") version "1.9.24"
}

group = "org.igor_shaula"
version = "0.2.4"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}