plugins {
    kotlin("jvm")
    `maven-publish`
    id("org.jetbrains.dokka")
}

group = "dev.gimme.gimmemc"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/groups/public")
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))

    // Spigot
    compileOnly("org.spigotmc:spigot-api:1.17-R0.1-SNAPSHOT")

    // Test
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "14"
        allWarningsAsErrors = true
        freeCompilerArgs = listOf("-progressive")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
