plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.realm)
}

android {
    namespace = "xyz.teamgravity.mongodbrealmdemo"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "xyz.teamgravity.mongodbrealmdemo"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // compose
    implementation(platform(libs.compose))
    implementation(libs.compose.ui)
    implementation(libs.compose.graphics)
    implementation(libs.compose.preview)
    implementation(libs.compose.material3)

    // compose activity
    implementation(libs.compose.activity)

    // compose lifecycle
    implementation(libs.compose.lifecycle)

    // compose viewmodel
    implementation(libs.compose.viewmodel)

    // compose hilt
    implementation(libs.compose.hilt)

    // core
    implementation(libs.core)

    // collections
    implementation(libs.collections)

    // hilt
    implementation(libs.hilt)
    ksp(libs.dagger.compiler)

    // coroutines
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)

    // realm
    implementation(libs.realm)
}