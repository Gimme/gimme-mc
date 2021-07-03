plugins {
    id("gimme-mc")
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    // API
    api(project(":gimme-mc-item"))
    api("com.github.Gimme.command:gimme-command-mc:main-SNAPSHOT")
}
