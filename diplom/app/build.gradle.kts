plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.diplom"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.diplom"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        viewBinding = true
    }
}

configurations {
    all {
        exclude(group = "org.apache.xmlbeans", module = "xmlbeans")
        exclude(group = "org.apache.poi", module = "poi-ooxml-schemas")
        exclude(group = "org.apache.commons", module = "commons-compress")
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")

    implementation("com.yandex.android:maps.mobile:4.5.1-full")

    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.itextpdf:itextg:5.5.10")
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // Apache POI dependencies
    implementation("org.apache.poi:poi:3.17")
    implementation("org.apache.poi:poi-ooxml:3.17")
    implementation("org.apache.poi:poi-ooxml-schemas:3.17")
    implementation("org.apache.xmlbeans:xmlbeans:2.6.0")
    implementation("org.apache.commons:commons-compress:1.19")
    implementation("commons-io:commons-io:2.6")


    // XML dependencies for Android
    implementation("javax.xml.stream:stax-api:1.0-2")
    implementation("com.fasterxml.woodstox:woodstox-core:6.5.1")
    implementation("org.codehaus.woodstox:stax2-api:4.2.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
} 