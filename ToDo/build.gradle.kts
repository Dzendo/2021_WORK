// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Файл сборки верхнего уровня, в который можно добавить параметры конфигурации,
// общие для всех подпроектов/модулей.
// _14.03.2023 canary 8 + gradle 8.0.2
plugins {
    // Объявляются классы Gradle, которые нужны для сборки зависимостей
    id ("com.android.application") version "8.2.0-alpha02" apply false
    id ("com.android.library") version "8.2.0-alpha02" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.21" apply false  //
    // Нужно для передачи параметров в навигации проекта
    id ("androidx.navigation.safeargs.kotlin")  version "2.6.0-beta01" apply false   // 2.6.0-alpha07  "2.5.3"
    // Нужно для внедрения зависимостей hilt
    id ("com.google.dagger.hilt.android")  version "2.46" apply false
    id ("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false  // 1.8.20-RC-1.0.9  "1.8.10-1.0.9"
}
