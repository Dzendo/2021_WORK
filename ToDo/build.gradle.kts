// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Файл сборки верхнего уровня, в который можно добавить параметры конфигурации,
// общие для всех подпроектов/модулей.
// _14.03.2023 canary 8 + gradle 8.0.2
plugins {
    // Объявляются классы Gradle, которые нужны для сборки зависимостей
    id ("com.android.application") version "8.2.0-alpha09" apply false
    id ("com.android.library") version "8.2.0-alpha09" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.0-RC" apply false  // 1.9.0-Beta 1.8.21
    // Нужно для передачи параметров в навигации проекта
    id ("androidx.navigation.safeargs.kotlin")  version "2.7.0-beta01" apply false   //  2.7.0-alpha01 2.6.0-rc02
    // Нужно для внедрения зависимостей hilt
    id ("com.google.dagger.hilt.android")  version "2.46.1" apply false  // 2.46.1
    id ("com.google.devtools.ksp") version "1.9.0-RC-1.0.11" apply false  // 1.9.0-Beta-1.0.11 1.8.21-1.0.11
}
