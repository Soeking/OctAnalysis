plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.apollographql.apollo3") version "4.0.0-alpha.1"
}

android {
    namespace = "net.soeki.octanalysis"
    compileSdk = 33

    defaultConfig {
        applicationId = "net.soeki.octanalysis"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
        targetCompatibility = org.gradle.api.JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.core:core-ktx:1.10.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("com.apollographql.apollo3:apollo-runtime:4.0.0-alpha.1")
    implementation("com.apollographql.apollo3:apollo-adapters:4.0.0-alpha.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("com.patrykandpatrick.vico:compose-m3:1.12.0")
}

apollo {
    service("service") {
        packageName.set("net.graphql")
        mapScalar("DateTime", "kotlinx.datetime.LocalDateTime", "com.apollographql.apollo3.adapter.KotlinxLocalDateTimeAdapter")
    }
}