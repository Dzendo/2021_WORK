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
    compileSdk = 33

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
        applicationId = "com.app4web.asdzendo.todo"
        minSdk = 26     // Android 7.1.1
        targetSdk = 33
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
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.8.20-RC")
    implementation ("androidx.core:core-ktx:1.10.0-rc01")  // 1.10.0-alpha01
    implementation ("androidx.appcompat:appcompat:1.7.0-alpha02")  // 1.7.0-alpha02
    //implementation ("androidx.fragment:fragment-ktx:1.5.5")  // 1.6.0-alpha05
    implementation ("com.google.android.material:material:1.9.0-alpha02")  // 1.9.0-alpha01
    implementation ("androidx.constraintlayout:constraintlayout:2.2.0-alpha08")   // 2.2.0-alpha07
    implementation ("androidx.legacy:legacy-support-v4:1.0.0") //  обратное API от 14 android 4 - ScrollChildSwipeRefreshLayout 1.1.0
    implementation ("androidx.recyclerview:recyclerview:1.3.0")  // 1.3.0-rc01

    implementation ("androidx.paging:paging-runtime-ktx:3.2.0-alpha04")   // 3.2.0-alpha04

    // Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.6.0-alpha07")  // 2.6.0-alpha05
    implementation ("androidx.navigation:navigation-ui-ktx:2.6.0-alpha07") // 2.6.0-alpha05

    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")  // liveData       // 2.6.0-beta01
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0") // ViewModelScope, // 2.6.0-beta01
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")                     // 2.6.0-beta01

    // Room dependencies
    implementation ("androidx.room:room-runtime:2.5.0")  //
    implementation ("androidx.room:room-ktx:2.5.0")
    implementation ("androidx.room:room-paging:2.5.0")
   // optional - Kotlin Extensions and Coroutines support for Room
    kapt ("androidx.room:room-compiler:2.5.0")  //

    // Coroutines for getting off the UI thread
    // implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Hilt dependencies
    implementation ("com.google.dagger:hilt-android:2.45")
    kapt ("com.google.dagger:hilt-android-compiler:2.45")  // ksp вылетает
    //implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")  //??
    kapt ("androidx.hilt:hilt-compiler:1.0.0")   // ??  May 05, 2021

    // Logging
    implementation ("com.jakewharton.timber:timber:5.0.1")

    // Тестовые библиотеки стандарт шаблона + room
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    //implementation ("androidx.test.espresso:espresso-idling-resource:3.5.1")
    //testImplementation ("androidx.room:room-testing:2.5.0")
    //testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // AndroidX Test - JVM testing
    /*
    testImplementation ("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation ("androidx.test:core-ktx:1.5.0")
    testImplementation ("org.robolectric:robolectric:4.9.2")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")  // 2.2.0-alpha01
    testImplementation ("org.hamcrest:hamcrest-all:1.3")
     */
}
