import org.gradle.accessors.dm.LibrariesForLibs.VersionAccessors
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias { libs.plugins.kotlin.multiplatform }
    alias { libs.plugins.android.library } apply true
    alias { libs.plugins.dokka }
    `maven-publish`
    jacoco
}

data class ProjectInfo(
    val groupId: String = libs.versions.project.group.id.get(),
    val artifactId: String = libs.versions.project.artifact.id.get(),
    val moduleId: String? = null,
    val major: String = libs.versions.project.version.major.get(),
    val minor: String = libs.versions.project.version.minor.get(),
    val patch: String = libs.versions.project.version.patch.get(),
    val javaToolchain: String = libs.versions.java.toolchain.get(),
    val javaBytecode: String = libs.versions.java.bytecode.get(),
    val androidCompileSdk: Int = libs.versions.project.android.compileSdk.get().toInt(),
    val androidMinSdk: Int = libs.versions.project.android.minSdk.get().toInt(),
) {
    val androidNamespace: String by lazy {
        moduleId?.let { "${groupId}.${artifactId}.$it" } ?: "${groupId}.${artifactId}"
    }
    val moduleArtifactId: String by lazy {
        moduleId?.let { "${artifactId}-$it" } ?: artifactId
    }
    val semanticVersion: String by lazy {
        "$major.$minor.$patch"
    }
}

val info = ProjectInfo()
group = info.groupId
version = info.semanticVersion

tasks.wrapper {
    gradleVersion = "latest"
}

kotlin {
    // Require explicit visibility & return types.
    explicitApi()

    // Enable browsing compiled bytecode in consumer's IDE.
    withSourcesJar(publish = true)

    // JDK version used by compiler & tooling.
    jvmToolchain(jdkVersion = info.javaToolchain.toInt())

    compilerOptions {
        // Free compiler args
        freeCompilerArgs.addAll(
            "-opt-in=kotlin.experimental.ExperimentalTypeInference",
            "-opt-in=kotlin.contracts.ExperimentalContracts",
        )

        // Enable extra K2 warnings.
        extraWarnings = true
    }

    // Define target platforms (and target-specific compiler options, publishing settings, tasks, artefacts, etc).
    jvm {
        // Enable publishing decompiled source code in consumer's IDE.
        isSourcesPublishable = true

        java {
            compilerOptions {
                // Target version of the generated JVM bytecode.
                jvmTarget = JvmTarget.fromTarget(target = info.javaBytecode)
            }

            sourceCompatibility = JavaVersion.toVersion(info.javaToolchain)
            targetCompatibility = JavaVersion.toVersion(info.javaBytecode)
        }
    }
    androidTarget {
        // Enable publishing decompiled source code in consumer's IDE.
        isSourcesPublishable = true

        android {
            compilerOptions {
                jvmTarget = JvmTarget.fromTarget(target = info.javaBytecode)
            }
            compileOptions {
                sourceCompatibility = JavaVersion.toVersion(info.javaToolchain)
                targetCompatibility = JavaVersion.toVersion(info.javaBytecode)
            }
        }
        compilerOptions.jvmTarget = JvmTarget.fromTarget(target = info.javaBytecode)
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.kotlin.bom))
            implementation(libs.bundles.core)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test)
        }
        androidMain {
            android {
                namespace = info.androidNamespace
                compileSdk = info.androidCompileSdk
                defaultConfig.minSdk = info.androidMinSdk
            }
            dependencies {
                compileOnly(libs.bundles.androidMain)
            }
        }
        jvmMain.dependencies {
            compileOnly("org.slf4j:slf4j-api:2.0.9")
        }
        jvmTest.dependencies {
            implementation(libs.junit5)
        }
    }
}

// The `Test` type is only used for JVM tests.
tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    finalizedBy(tasks.named("jacocoTestReport"))
    reports {
        junitXml.required = true
        html.required = true
    }
}

jacoco {
    reportsDirectory = layout.buildDirectory.dir("reports/jacoco")

    tasks.register<JacocoReport>("jacocoTestReport") {
        dependsOn(tasks.named("jvmTest"))
        reports {
            xml.required = true   // XML report for CI summary
            html.required = true  // HTML report for local inspection
            html.outputLocation = layout.buildDirectory.dir("reports/jacoco/html")
        }
    }
}

tasks.register<Jar>("dokkaJar") {
    from(tasks.dokkaGeneratePublicationHtml)
    dependsOn(tasks.dokkaGeneratePublicationHtml)
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        register<MavenPublication>(name = "${info.moduleArtifactId}-maven-artifact") {
            from(components["kotlin"])
            groupId = info.groupId
            artifactId = info.moduleArtifactId
            version = info.semanticVersion

            // Include Dokka-generated Javadoc in the publication.
            artifact(tasks.named("dokkaJar"))
        }
    }
    repositories {
        // Publishing to local M.2 repository
        mavenLocal()

        // Publishing to GitHub Packages
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/adjmunro/kotlin-utils")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }

        // TODO Publish to Sonatype OSSRH / Maven Central
//        maven {
//            name = "OSSRH"
//            url = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
//            credentials {
//                username = System.getenv("MAVEN_USERNAME")
//                password = System.getenv("MAVEN_PASSWORD")
//            }
//        }
    }
}
