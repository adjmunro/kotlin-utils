import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias { libs.plugins.kotlin.multiplatform }
}


data class ProjectInfo(
    val artifactId: String,
    val groupId: String = libs.versions.project.group.id.get(),
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

val info = ProjectInfo(artifactId = "bdd")
group = info.groupId
version = info.semanticVersion

kotlin {
    // Require explicit visibility & return types.
    explicitApi()

    // Enable browsing compiled bytecode in consumer's IDE.
    withSourcesJar(publish = true)

    // JDK version used by compiler & tooling.
    jvmToolchain(jdkVersion = info.javaToolchain.toInt())

    compilerOptions {
        // Free compiler args
        freeCompilerArgs.addAll("-opt-in=kotlin.contracts.ExperimentalContracts")

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

    sourceSets {
        commonMain.dependencies {

        }
        commonTest.dependencies {

        }
    }
}
