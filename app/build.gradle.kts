plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.mikepenz.aboutlibraries.plugin") version "11.6.3"
}

tasks.register<Copy>("copyAboutLibrariesJson") {
    // This gets the variant name from the Gradle task names (like assembleDebug, mergeReleaseAssets, etc.)
    val variantName = gradle.startParameter.taskNames
        .firstOrNull { it.contains("Debug", ignoreCase = true) || it.contains("Release", ignoreCase = true) }
        ?.let { task ->
            Regex("(debug|release)", RegexOption.IGNORE_CASE)
                .find(task)?.value?.lowercase()
        } ?: "debug" // fallback if variant can't be detected

    val sourceFile = file("$buildDir/intermediates/libraries.json/$variantName/libraries.json")

    from(sourceFile)
    into("src/main/assets")
    rename("libraries.json", "aboutlibraries.json")

    doFirst {
        if (!sourceFile.exists()) {
            throw GradleException("Missing generated libraries.json for variant '$variantName'. Did you run :app:exportLibraries?")
        }
    }
}

tasks.named("preBuild") {
    dependsOn("copyAboutLibrariesJson")
}

android {
    namespace = "khw15.eventsdicoding"
    compileSdk = 35

    defaultConfig {
        applicationId = "khw15.eventsdicoding"
        minSdk = 26
        targetSdk = 35
        versionCode = 24
        versionName = "2.4.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://event-api.dicoding.dev/\"")
        buildConfigField("String", "GITHUB_URL", "\"https://github.com/khw315/EventsforDicoding\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.browser)
    implementation(libs.lottie)
    implementation(libs.shimmer)
    implementation(libs.androidx.core.splashscreen)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)

    // Coroutine support
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // viewModelScope
    implementation(libs.androidx.lifecycle.livedata.ktx) // liveData
    implementation(libs.androidx.room.ktx)

    // Settings
    implementation(libs.androidx.preference)

    // for dark mode
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Work manager
    implementation(libs.androidx.work.runtime)

    implementation(libs.aboutlibraries)

}