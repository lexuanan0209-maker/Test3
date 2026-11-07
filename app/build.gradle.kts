plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.app.quanlyquy"
    compileSdk = 37 // Hoặc giữ nguyên version của bạn

    defaultConfig {
        applicationId = "com.app.quanlyquy"
        minSdk = 26 // Nên để 24 hoặc 26 để tương thích tốt với mọi thiết bị
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        viewBinding = true // Bật thêm viewBinding nếu muốn dùng, hoặc giữ mặc định
    }
}

dependencies {
    // --- CÁC THƯ VIỆN XML & JSON BẮT BUỘC CHO ĐỀ TÀI CỦA BẠN ---
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.code.gson:gson:2.10.1") // Thư viện xử lý JSON

    // --- Thư viện nền tảng khác ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // (Giữ lại Compose nếu dự án có dùng, không thì có thể để nguyên)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}