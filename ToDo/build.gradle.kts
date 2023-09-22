// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Файл сборки верхнего уровня, в который можно добавить параметры конфигурации,
// общие для всех подпроектов/модулей.
// _14.03.2023 canary 8 + gradle 8.0.2
plugins {
    // Объявляются классы Gradle, которые нужны для сборки зависимостей
    id ("com.android.application") version "8.3.0-alpha05" apply false
    id ("com.android.library") version "8.3.0-alpha05" apply false
    id ("org.jetbrains.kotlin.android") version "1.9.20-Beta2" apply false
    // Нужно для передачи параметров в навигации проекта
    id ("androidx.navigation.safeargs.kotlin")  version "2.7.2" apply false
    // Нужно для внедрения зависимостей hilt
    id ("com.google.dagger.hilt.android")  version "2.48" apply false
    id ("com.google.devtools.ksp") version "1.9.20-Beta2-1.0.13" apply false
}
