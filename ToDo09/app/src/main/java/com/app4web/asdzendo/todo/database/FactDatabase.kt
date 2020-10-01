/*
 * Copyright 2019, The Android Open Source Project
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
 */

package com.app4web.asdzendo.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app4web.asdzendo.todo.launcher.BASE_IN_MEMORY
import com.app4web.asdzendo.todo.launcher.FACT_TODO_DATABASE_NAME

/**
 * A database that stores Fact information.
 * База данных, которая хранит информацию о ночном сне.
 * And a global method to get access to the database.
 * И глобальный метод получения доступа к базе данных.
 *
 * This pattern is pretty much the same for any database, so you can reuse it.
 * Этот шаблон практически одинаков для любой базы данных, поэтому вы можете использовать его повторно.
 */
// вызывается из ToDoInjectorUtils для создания/подключения базы данных
// при создания/подключения FactRepository через getFactRepository
// для передачи в ViewModel через ViewModelFactory
// а ViewModel создаются/подключаются из фрагментов
// Fragment <- ViewModel <- ViewModelFactory <- provideToDoActitityViewModelFactory
// <- getFactRepository <- FactRepository <- FactDatabase <- FactDatabaseDao <- @Entity Fact
@Database(entities = [Fact::class], version = 1, exportSchema = false)
//@TypeConverters(CalendarConverters::class, PaemiConverters::class)
abstract class FactDatabase : RoomDatabase() {
    /**
     * Connects the database to the DAO.
     * Подключает базу данных к DAO.
     */
    abstract val factDatabaseDao: FactDatabaseDao
    //abstract fun factDatabaseDaoD(): FactDatabaseDao  // Дает Ошибку трансляции fs
    /**
     * Define a companion object, this allows us to add functions on the FactDatabase class.
     * Определите сопутствующий объект, это позволит нам добавить функции в класс базы данных Fact.
     *
     * For example, clients can call `FactDatabase.getInstance(context)`
     * to instantiate a new FactDatabase.
     * Например, клиенты могут вызвать "базу данных фактов".getInstance (контекст)`
     * создать экземпляр новой базы данных фактов.
     *
     */
    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         * Экземпляр будет хранить ссылку на любую базу данных, возвращенную через getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         * Это поможет нам избежать многократной инициализации базы данных, которая является дорогостоящей.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         *  Значение переменной volatile никогда не будет кэшироваться,
         *  а все записи и записи будут выполняться в режиме реального времени.
         * чтение будет выполняться в основную память и из нее.
         * Это означает, что изменения, внесенные одним потоком к общим данным виден другим потокам.
         */
        // For Singleton instantiation
        //  Для одноэлементный экземпляр SunFlower
        // instance хранит и отдает ссылку на FactDatabase по требованию getInstance
        @Volatile private var instance: FactDatabase? = null

        // INSTANCE Переменная будет хранить ссылку на базу данных, когда один был создан.
        // Это поможет вам избежать повторного открытия соединений с базой данных, что дорого.

        /**
         * Helper function to get the database.
         * Вспомогательная функция для получения базы данных.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         * Если база данных уже была получена, то будет возвращена предыдущая база данных.
         * В противном случае создайте новую базу данных.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         * Эта функция ориентирована на многопотоковое исполнение, и нужно кэшировать результаты для нескольких баз данных
         * звонки, чтобы избежать накладных расходов.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         * Это пример простого Одноэлементного шаблона, который принимает другой Одноэлемент в качестве
         * спор в Котлине.
         *
         * To learn more about Singleton read the wikipedia article:
         * Чтобы узнать больше о синглтоне читайте статью в Википедии:
         * https://en.wikipedia.org/wiki/Singleton_pattern
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        // getInstance() метод с Context параметром, который понадобится построителю базы данных.
        fun getInstance(context: Context): FactDatabase =
            instance ?: synchronized(this) {     // только один поток выполнения одновременно может войти в этот блок кода,
                instance ?: buildDatabase(context).also { instance = it }
            }
        // Создайте и предварительно заполните базу данных.
        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): FactDatabase =
            if (BASE_IN_MEMORY)
            Room.inMemoryDatabaseBuilder(context, FactDatabase::class.java)
                    .fallbackToDestructiveMigration()
                    .build()
            else
                Room.databaseBuilder(context, FactDatabase::class.java, FACT_TODO_DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
    }
}
