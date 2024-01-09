// Настройка сборки зависимостей модуля. Здесь все, чего нет в зависимостях проекта
// https://habr.com/ru/post/568792/
plugins {
    // Два первых стандрт обязательны для Котлина
    id ("com.android.application")
    id ("kotlin-android")
    // можно использовать такие библиотеки, как Hilt
    id ("com.google.devtools.ksp")
    // или Data Binding
    id ("kotlin-kapt")
    // Необходимо для класса передачи параметров в навигации проекта
    id ("androidx.navigation.safeargs.kotlin")
    //id ("kotlin-android-extensions") - Устарела теперь только 'kotlin-parcelize'
    // Нужно для внедрения зависимостей Hilt
    id ("dagger.hilt.android.plugin")
}

android {
//    compileSdkPreview = "UpsideDownCakePrivacySandbox"
//    compileSdkPreview = "UpsideDownCake"
    compileSdk = 34
    //targetSdkPreview = "UpsideDownCake"

    //https://developer.android.com/studio/build/configure-app-module#set-namespace
    namespace = "com.app4web.asdzendo.todo"
    // Включить Спецтехнологии:
    buildFeatures {
        // построит из xml спецклассы java см. java (generated)
        dataBinding = true
        viewBinding = true  // будем использовать для других
    }
    dataBinding {
        // может и не нужен??
        // enabledForTests = true
    }

    defaultConfig {
        // !! Имя пакета !! оно в смарт уезжает и в Google play, Firebase, AdMob
//        compileSdkPreview = "UpsideDownCakePrivacySandbox"
//        targetSdkPreview = "UpsideDownCake"
        applicationId = "com.app4web.asdzendo.todo"
        minSdk = 26     // Android 7.1.1
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        // ???
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    // ???
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        //sourceCompatibility sourceCompatibility(JavaVersion.VERSION_11)

        // Обязательно 1_8 для этой архитектуры
        //targetCompatibility (JavaVersion.VERSION_11)
    }
    kotlinOptions {
        // Обязательно для использования Room и др.
        jvmTarget = "17"
        // ??? Переписал откуда-то зачем не помню
        // freeCompilerArgs += ["-Xopt-in=kotlin.RequiresOptIn"]
        // Enable Coroutines and Flow APIs из sunflowers
        // freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        // freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.FlowPreview"
    }
    //buildToolsVersion '33.0.1'
    // Always show the result of every unit test when running via command line, even if it passes.
    // Всегда показывайте результат каждого модульного теста при запуске через командную строку, даже если он проходит.
    // избавиться от ошибки  No such manifest file: ./AndroidManifest.xml - НЕ работает дает ошибку ROBO
   /* testOptions.unitTests {
        includeAndroidResources = true
        // Robolectric
        // RuntimeException c причиной — method not mocked при попытке запустить тест кода вызывающего какой — либо метод фреймворка.
        // А если использовать следующую опцию в Gradle -
        returnDefaultValues = true
        // то, RuntimeException брошен не будет. Такое поведение может приводить к тяжело детектируемым ошибкам в тестах.
    }*/
}
// Перечисление зависимостей от библиотек java/kotlin/androidx/google/...
// Они все берутся из repositories проекта: сейчас - google() и jcenter()
dependencies {
    // Стандарт для Android Kotlin; Если есть -ktx, ставить ее (там плюс идиомы)
    implementation ("org.jetbrains.kotlin:kotlin-reflect:2.0.0-Beta2")
    implementation ("androidx.core:core-ktx:1.13.0-alpha02")
    implementation ("androidx.appcompat:appcompat:1.7.0-alpha03")
    //implementation ("androidx.fragment:fragment-ktx:1.5.7")  // 1.6.0-rc01

    // https://mvnrepository.com/artifact/com.google.android.material/material
    runtimeOnly("com.google.android.material:material:1.12.0-alpha02")  // 1.11.0 1.12.0-alpha02
    implementation ("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")   //
    implementation ("androidx.legacy:legacy-support-v4:1.0.0") //  обратное API от 14 android 4 - ScrollChildSwipeRefreshLayout 1.1.0
    implementation ("androidx.recyclerview:recyclerview:1.4.0-alpha01")  //

    implementation ("androidx.paging:paging-runtime-ktx:3.3.0-alpha02")   //

    // Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.6")

    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0-rc02")  // liveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0-rc02") // ViewModelScope,
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0-rc02")

    // Room dependencies
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.room:room-paging:2.6.1")
   // optional - Kotlin Extensions and Coroutines support for Room
    ksp ("androidx.room:room-compiler:2.6.1")  //

    // Coroutines for getting off the UI thread
    // implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0-RC2")

    // Hilt dependencies
    implementation ("com.google.dagger:hilt-android:2.50")
    ksp ("com.google.dagger:hilt-android-compiler:2.50")  // ksp вылетает
    //implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")  //??

    // Logging
    implementation ("com.jakewharton.timber:timber:5.0.1")

    // Тестовые библиотеки стандарт шаблона + room
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.2.0-alpha02")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.0-alpha02")
    //implementation ("androidx.test.espresso:espresso-idling-resource:3.6.0-alpha01")
    //testImplementation ("androidx.room:room-testing:2.6.0-alpha01")
    //testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    // AndroidX Test - JVM testing
    /*
    testImplementation ("androidx.test.ext:junit-ktx:1.2.0-alpha01")
    testImplementation ("androidx.test:core-ktx:1.6.0-alpha01")
    testImplementation ("org.robolectric:robolectric:4.10.3")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("org.hamcrest:hamcrest-all:1.3")
     */
}
