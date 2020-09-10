/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app4web.asdzendo.todo.database

import com.app4web.asdzendo.todo.launcher.FilterDateEnd
import com.app4web.asdzendo.todo.launcher.FilterDateStart
import com.app4web.asdzendo.todo.launcher.PAEMI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*



/**
 * Repository module for handling data operations.
 * Модуль репозитория для обработки операций с данными.
 * вызывается из всех ViewModels сам вызывает FactDatabaseDao функции
 */
class FactRepository private constructor(private val factDao: FactDatabaseDao) {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    // Возвращает обычное Fact?
    fun get(factID:Int): Fact? = factDao.get(factID)

    fun insert(fact: Fact?) =
            applicationScope.launch {
                var newFact = Fact()
                if (fact != null) newFact = fact
                newFact.factId = 0
                factDao.insert(newFact)
            }

    fun update(fact: Fact?) =
            applicationScope.launch {
                if (fact != null)
                   if (get(fact.factId) != null)
                        factDao.update(fact)
            }

    fun delete(fact: Fact?) =
            applicationScope.launch {
                if (fact != null)
                    if (get(fact.factId) != null)
                        factDao.delete(fact)
            }

    fun clear() =
        applicationScope.launch {
            factDao.clear() // Заполнить заново базу данных
            Timber.i("ToDoFactRepository fact_base_clearing База очищена  ")
        }

    // LiveData<Int>
    fun count() = factDao.getCount()

    // отдает LiveData<List<Fact>>
    fun getAll() = factDao.getAll()

    // отдает PagingSource<Int, Fact>
    fun getAllPage() = factDao.getAllPage()

    // отдает LiveData<List<Fact>>
    fun getAllFacts() = factDao.getAllFacts()

    // отдает PagingSource<Int, Fact>
    fun getAllFactsPage() =
            factDao.getAllFactsPage()

    // Основной фильтр по PAEMI отдает LiveData<List<Fact>>
    fun getAllPAEMIFacts(paemi: Int) = factDao.getAllPAEMIFacts(paemi)

    // Основной фильтр по PAEMI отдает PagingSource<Int, Fact>
    fun getAllPAEMIFactsPage(paemi: PAEMI) =
            factDao.getAllPAEMIFactsPage(paemi,FilterDateStart,FilterDateEnd)

    // отдает LiveData<Fact>
    fun getFactWithId(factID: Int) = factDao.getFactWithId(factID)

    // Дозаполнение начальных данных
    suspend fun addFactDatabaseAll(countFacts: Int = 100) =
        applicationScope.launch {
            val addCount = factDao.insertAll(factContent(countFacts))
            Timber.i("ToDoFactRepository Add Database Строк записи = $countFacts * 7 = ${addCount.size}")
        }
    fun addFactDatabase(countFacts: Int = 1000) {
         applicationScope.launch {
            for (count in 1..(countFacts / 10_000)) {
                val job100 = applicationScope.launch {
                    val addCount = factDao.insertAll(factContent(10000))
                }
                 job100.join() // wait until child coroutine completes
                Timber.i("ToDoFactRepository Add Database Пачка записи = ${count}--> ${countFacts/10_000}")
            }
            addFactDatabaseAll(countFacts % 10000)
            Timber.i("ToDoFactRepository Add Database Последняя записи = ${countFacts % 1000 * 7}")
        }
    }
    // Заполнение дополнительной пачки строк для базы в количестве countFacts * 7
    private fun factContent(count: Int = 100): List<Fact>  {


        val FACTS: MutableList<Fact> = ArrayList()

        // Add some sample items.
        for (id in 1..count)
            for (paemi in PAEMI.values()) {
                val fact = Fact(paemi = paemi, nameShort = "$id Факт", name = "Факт полностью: $id")
                with (fact) {
                    //calendar.add(Calendar.DAY_OF_YEAR, daysToAdd)
                    data = GregorianCalendar.getInstance()
                    data.set(GregorianCalendar.YEAR,(2000..2040).random())
                    data.set(GregorianCalendar.DAY_OF_YEAR,(1..365).random())
                }
                FACTS.add(fact)
            }
        Timber.i("ToDoFactRepository Add List Строк записи = $count * 7 = ${count * 7}")
        return FACTS
    }

    // Вызывает создание/восстановление ссылки на этот репозиторий с привязкой FactDatabaseDao
    companion object {    // For Singleton instantiation
        @Volatile private var instance: FactRepository? = null
        fun getInstance(factDao: FactDatabaseDao) =
                instance ?: synchronized(this) {
                    instance ?: FactRepository(factDao).also { instance = it }
                }
    }
}
