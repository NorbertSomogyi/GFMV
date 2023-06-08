import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.6.21"
val kotlinCoroutineVersion = "1.6.2"
val koinVersion = "3.3.3"
val mockkVersion = "1.10.6"
val junitVersion = "5.6.0"

plugins {
    java

    kotlin("jvm") version "1.7.22"
    kotlin("kapt") version "1.7.22"

    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "hu.modelverifier"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":core"))
    implementation(project(":uml"))

    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutineVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")


    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    testImplementation("io.mockk:mockk:$mockkVersion")

    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("dmla-language")
    archiveClassifier.set("")
    archiveVersion.set("")
}


tasks.test {
    useJUnitPlatform()
}

tasks.register<ShadowJar>("testJar") {
    archiveClassifier.set("tests")
    from(sourceSets.main.get().output, sourceSets.test.get().output)
    configurations = listOf(project.configurations.testRuntimeClasspath.get())
}

kapt {
    useBuildCache = true
    includeCompileClasspath = false
}