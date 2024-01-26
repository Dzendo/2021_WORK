// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Файл сборки верхнего уровня, в который можно добавить параметры конфигурации,
// общие для всех подпроектов/модулей.
// _14.03.2023 canary 8 + gradle 8.0.2
plugins {
    // Объявляются классы Gradle, которые нужны для сборки зависимостей
    id ("com.android.application") version "8.4.0-alpha07" apply false
    id ("com.android.library") version "8.4.0-alpha07" apply false
//    id ("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id ("org.jetbrains.kotlin.android.extensions") version "2.0.0-Beta3" apply false
    // Нужно для передачи параметров в навигации проекта
    id ("androidx.navigation.safeargs.kotlin")  version "2.7.6" apply false
    // Нужно для внедрения зависимостей hilt
    id ("com.google.dagger.hilt.android")  version "2.50" apply false
    id ("com.google.devtools.ksp") version "2.0.0-Beta3-1.0.17" apply false
}
