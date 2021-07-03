plugins {
    id("gimme-mc")
}

repositories {
    mavenLocal()
}

dependencies {
    // API
    api(project(":gimme-mc-item"))
    api("dev.gimme.command:gimme-command-mc:0.1.0-SNAPSHOT")
}
