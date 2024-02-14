import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.konfig)
}

fun <T: Any> propOfDef(propertyName: String, defaultValue: T): T {
    val props = Properties()
    try {
        FileInputStream("local.properties").use { props.load(it) }
    } catch (e: Exception) {
        println("Error reading local.properties: ${e.message}")
    }

    @Suppress("UNCHECKED_CAST")
    val propertyValue = props[propertyName] as? T
    return propertyValue ?: defaultValue
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.voyager.navigator)
            implementation(libs.composeImageLoader)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatformSettings)
            implementation(libs.koin.core)
            implementation(libs.supabase.postgrest)
            implementation(libs.supabase.storage)
            implementation(libs.supabase.auth)
            implementation(libs.platform.settings)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activityCompose)
            implementation(libs.compose.uitooling)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.ktor.client.js)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }
}

buildkonfig {
    packageName = "guild.hackathon"
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "SUPABASE_URL", propOfDef("SUPABASE_URL", "${System.getenv("SUPABASE_URL")}"))
        buildConfigField(FieldSpec.Type.STRING, "SUPABASE_KEY", propOfDef("SUPABASE_KEY", "${System.getenv("SUPABASE_KEY")}"))
    }
}

android {
    namespace = "guild.hackathon"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        applicationId = "guild.hackathon.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "guild.hackathon.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}
