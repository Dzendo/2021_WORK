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
class FactRepository private constructor( val factDao: FactDatabaseDao) {
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

    fun addFactDatabase(countFacts: Long = 65L) =  // Дозаполнение начальных данных
        applicationScope.launch {
            val addCount = factDao.insertAll(factContent(countFacts))
            Timber.i("ToDoFactRepository Add Database Строк записи = $countFacts * 7 = ${addCount.size}")
        }

    fun clear() =
        applicationScope.launch {
            factDao.clear() // Заполнить заново базу данных
            Timber.i("ToDoFactRepository fact_base_clearing База очищена  ")
        }

    // LiveData<Int>
    fun count() = factDao.getCount()

    // отдает LiveData<List<Fact>>
    //fun getAll() = factDao.getAll()

    // отдает LiveData<List<Fact>>
    fun getAll() = factDao.getAll()

    // отдает PagingSource<Int, Fact>
    fun getAllPage() = factDao.getAllPage()

    // отдает LiveData<List<Fact>>
    fun getAllP() = factDao.getAllP()

    // отдает LiveData<List<Fact>>
    fun getAllFacts() = factDao.getAllFacts()

    // отдает PagingSource<Int, Fact>
    fun getAllFactsPage() = factDao.getAllFactsPage()

    // Основной фильтр по PAEMI отдает LiveData<List<Fact>>
    fun getAllPAEMIFacts(paemi: String) = factDao.getAllPAEMIFacts(paemi)

    // Основной фильтр по PAEMI отдает PagingSource<Int, Fact>
    fun getAllPAEMIFactsPage(paemi: String?) = factDao.getAllPAEMIFactsPage(paemi)

    // отдает LiveData<Fact>
    fun getFactWithId(factID: Int) = factDao.getFactWithId(factID)


    // Заполнение дополнительной пачки строк для базы в количестве countFacts * 7
    private fun factContent(countFacts: Long = 65L): List<Fact>  {

        //val PAEMI: List<String> = arrayListOf(" ","P","A","E","M","I","S")
        val FACTS: MutableList<Fact> = ArrayList()
        //private val PAEMI_MAP: MutableMap<Long, Fact> = HashMap()
        // Add some sample items.
        for (id in 1L..countFacts)
            for (paemi in PAEMI) {
                val fact = Fact(paemi = paemi, nameShort = "$id Факт", name = "Факт полностью: $id")
                with (fact) {
                    data = Date(Date().time - (countFacts/2 -id - 50 + (0..100).random()) * 86400000L)
                    //data = Date(Date().time - (countFacts -(0..100).random()*id) * 86400000L)
                    dataStart = Date(Date().time - (countFacts - id+1) * 1000)
                    dataEnd = Date(Date().time -( countFacts - id+1) * 100)
                    deadLine = Calendar.getInstance()
                    deadLine.add(Calendar.DATE,id.toInt())
                    duration = dataStart?.time?.let { dataEnd?.time?.minus(it) }
                }
                FACTS.add(fact)
                //     FACT_MAP[fact.factId] = fact
            }
        Timber.i("ToDoFactRepository Add List Строк записи = $countFacts * 7 = ${countFacts * 7}")
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
