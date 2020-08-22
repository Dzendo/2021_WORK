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
import java.util.ArrayList

/**
 * Repository module for handling data operations.
 */
class FactRepository private constructor(private val factDao: FactDatabaseDao) {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    fun insert(fact: Fact) =
            applicationScope.launch {
                factDao.insert(fact)
            }


    fun update(fact: Fact) =
            applicationScope.launch {
                factDao.update(fact)
            }


    fun delete(fact: Fact) =
            applicationScope.launch {
                factDao.delete(fact)
            }

    fun addFactDatabase(countFacts: Long = 65L) =  // Дозаполнение начальных данных
        applicationScope.launch {
            factDao.insertAll(factContent(countFacts))
            Timber.i("ToDoFactRepository Add Database Строк записи = $countFacts * 7 = ${countFacts * 7}")
        }

    fun clear() =
        applicationScope.launch {
            factDao.clear() // Заполнить заново базу данных
            Timber.i("ToDoFactRepository fact_base_clearing База очищена  ")
        }

    fun count() = factDao.getCount()

    fun getAllFacts() = factDao.getAllFacts()

    fun getAllPAEMIFacts(paemi:String) = factDao.getAllPAEMIFacts(paemi)

    fun getFactWithId(factID: Long) = factDao.getFactWithId(factID)



    private fun factContent(countFacts: Long = 65L): MutableList<Fact>  {

        //val PAEMI: List<String> = arrayListOf(" ","P","A","E","M","I","S")
        val FACTS: MutableList<Fact> = ArrayList()
        //private val PAEMI_MAP: MutableMap<Long, Fact> = HashMap()
        // Add some sample items.
        for (id in 1L..countFacts)
            for (paemi in PAEMI)
                FACTS.add((Fact(paemi = paemi, nameShort= "$id Факт", name= "Факт полностью: $id")))
        //     FACT_MAP[fact.factId] = fact
        return FACTS
    }

    companion object {    // For Singleton instantiation
        @Volatile private var instance: FactRepository? = null
        fun getInstance(factDao: FactDatabaseDao) =
                instance ?: synchronized(this) {
                    instance ?: FactRepository(factDao).also { instance = it }
                }
    }
}
