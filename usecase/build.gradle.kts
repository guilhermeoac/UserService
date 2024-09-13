plugins {
    id("java")
}

group = "com.ntd.userservice"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    implementation(project(mapOf("path" to ":input-boundary")))
    implementation(project(mapOf("path" to ":output-boundary")))
    implementation(project(mapOf("path" to ":entities")))
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
    implementation("org.springframework.data:spring-data-commons:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.4")
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.2.4")
}

tasks.test {
    useJUnitPlatform()
}