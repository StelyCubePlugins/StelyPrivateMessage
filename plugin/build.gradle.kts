plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(libs.jetbrains.annotations)
    implementation(libs.bungeecord.api) {
        exclude(group = "com.mojang", module = "brigadier")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

project.base.archivesName.set(rootProject.name)
group = "fr.stelycube.stelyprivatemessage"
version = "1.0.0-SNAPSHOT"

tasks.processResources {
    expand(Pair("version", version))
}
