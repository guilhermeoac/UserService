
plugins {
    id("java")
}

group = "com.guilhermecosta.java"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":output-boundary")))
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.springframework.data:spring-data-commons:3.2.4")

}
