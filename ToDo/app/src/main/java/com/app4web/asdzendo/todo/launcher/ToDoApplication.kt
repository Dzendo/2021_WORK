/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.app4web.asdzendo.todo.launcher

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlinx.coroutines.*


/**
 * onCreate is called before the first screen is shown to the user.
 * onCreate вызывается до того, как пользователю будет показан первый экран.
 *
 * Use it to setup any background tasks, running expensive setup operations in a background
 * thread to avoid delaying app start.
 * Используйте его для настройки любых фоновых задач, выполняя дорогостоящие операции настройки
 * в фоновом режиме поток, чтобы избежать задержки запуска приложения.
 *
 * Этот Application вызывается сразу из манифеста, т.к. android:name=".launcher.ToDoApplication",
 * а после него вызывается android:name=".launcher.ToDoActivity"
 * А если в Application стартовать фоновый поток, то он начнет выполняться, но в фоне, т.е.
 * не дожадаясь его окончания стартует активити;
 * А можно много потоков стартовать, а можно службы и сервисы здесь стартовать, но в фоне
 * А тогда они будут работать параллельно с клиентской активити.
 *
 */

class ToDoApplication : Application() {
    // Доломал Timber - чинить
    override fun onCreate() {
        super.onCreate()
        // стартуем корутину с вызовом из нее функции приостановки (Suspend) - Timber заткнулся
        //  CoroutineScope(Dispatchers.Default).launch {
        timberInit()  // Инициализировать Timber не блокирует основной поток:+ две задачи
        //  }
    }

    private fun timberInit() { // Инициализация Timber
        val timb = CoroutineScope(Dispatchers.Default).launch {
            Timber.plant(Timber.DebugTree())
            Timber.i("ToDoApplication timber READY ")
        }
/*
        CoroutineScope(Dispatchers.Default).launch {
            timb.join()
            Timber.i("ToDoApplication timber Init START_1")
            for (i in 0..1000) {
                delay(7000L)
                Timber.i("ToDoApplication Работа 1 шаг $i")
            }
            Timber.i("ToDoApplication timber Init FINISH_1")
        }

        CoroutineScope(Dispatchers.Default).launch {
            timb.join()
            Timber.i("ToDoApplication timber Init START_2")
            for (i in 0..1000) {
                delay(3000L)
                Timber.i("ToDoApplication Работа 2 шаг $i")
            }
            Timber.i("ToDoApplication timber Init FINISH_2")
        }

 */
    }

}
    /**
     * Override application to setup background work via WorkManager
     * Переопределение приложения для настройки фоновой работы через Диспетчер работ
     */
    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     * Настройка работы менеджер фоновых заданий, чтобы "взять" новую сеть данных ежедневно.
     */
// Важно отметить, что WorkManager.initialize должен вызываться изнутри onCreate без использования фонового потока,
// чтобы избежать проблем, возникающих при инициализации после использования WorkManager.
    // Шаг 1: Настройка повторяющейся работы
// Сделайте запрос PeriodWorkRequest: Это должно бежать один раз каждый день.
  /*  private fun setupRecurringWork() {
        // Определите ограничения: чтобы предотвратить выполнение работы, когда нет доступа к сети или устройство разряжено.
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)  // Unlimited
                .setRequiresBatteryNotLow(true)                 // Battery не разряжена
                .setRequiresCharging(true)                      // Стоит на зарядке
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)             // Устройство не используется
                    }
                }.build()  // Используйте build()метод для генерации ограничений из компоновщика.
            // переменную, которая использует PeriodicWorkRequestBuilder для создания PeriodicWorkRequest для вас RefreshDataWorker.
        /*val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints) //  Добавьте ограничения
                .build()
         */
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        // val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(16, TimeUnit.MINUTES)
        //       .build()

        //  Запланируйте работу как уникальную:
        // Получить экземпляр WorkManager и позвонить, enqueueUniquePeriodicWork чтобы запланировать работу.
        Timber.d("Periodic Work request for sync is scheduled")
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }*/
    // Если существует ожидающая (незавершенная) работа с тем же именем,
    // ExistingPeriodicWorkPolicy.KEEP параметр заставляет WorkManager сохранить предыдущую периодическую работу
    // и отклонить новый запрос на работу.




// В приложении для Android Application класс является базовым классом,
// который содержит все другие компоненты, такие как действия и службы.
// Когда создается процесс для вашего приложения или пакета,
// создается экземпляр Application класса (или любого подкласса Application) перед любым другим классом.
// Класс является хорошим местом , чтобы запланировать WorkManager.

/*
Шаг 2: Запланируйте WorkRequest с WorkManager
После того, как вы определите свой WorkRequest, вы можете запланировать это с WorkManager помощью enqueueUniquePeriodicWork()метода.
Этот метод позволяет добавить уникальное имя PeriodicWorkRequest в очередь,
где одновременно PeriodicWorkRequest может быть активным только одно из определенных имен.

Например, вы можете захотеть, чтобы была активна только одна операция синхронизации.
Если выполняется одна операция синхронизации, вы можете разрешить ее выполнение или заменить ее новой работой,
используя ExistingPeriodicWorkPolicy .

Чтобы узнать больше о способах планирования WorkRequest, см. WorkManagerДокументацию.
 */

/*
Лучшая практика:
onCreate()метод работает в основном потоке.
Выполнение длительной операции в onCreate() может заблокировать поток пользовательского интерфейса
и вызвать задержку загрузки приложения.
Чтобы избежать этой проблемы, запустите такие задачи, как инициализация Timber и планирование WorkManager
из основного потока внутри сопрограммы.
 */

