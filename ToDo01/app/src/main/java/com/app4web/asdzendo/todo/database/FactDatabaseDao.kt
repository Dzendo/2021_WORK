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

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * Defines methods for using the Fact class with Room.
 * Определяет методы для использования класса Fact с номером.
 */
@Dao
interface FactDatabaseDao {

    @Insert
    fun insert(fact: Fact)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     * При обновлении строки со значением, уже установленным в столбце,
     * заменяет старое значение на новое.
     *
     * @param fact new value to write
     */
    @Update
    fun update(fact: Fact)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     * Выбирает и возвращает строку, которая соответствует предоставленному id  что является нашим ключом.
     *
     * @param id startTimeMilli to match
     */
    @Query("SELECT * from fact_todo WHERE factId = :id")
    fun get(id: Long): Fact

    /**
     * Deletes all values from the table.
     * Удаляет все значения из таблицы.
     *
     * This does not delete the table, only its contents.
     * При этом таблица не удаляется, а только ее содержимое.
     */
    @Query("DELETE FROM fact_todo")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *Выбирает и возвращает все строки в таблице,
     * sorted by start time in descending order.
     * сортировка по времени начала в порядке убывания.
     */
    @Query("SELECT * FROM fact_todo ORDER BY factId DESC")
    fun getAllFacts(): LiveData<List<Fact>>

    /**
     * Selects and returns the latest night.
     * Выбирает и возвращает последнюю запись.
     */
    @Query("SELECT * FROM fact_todo ORDER BY factId DESC LIMIT 1")
    fun getLastFact(): Fact?

    /**
     * Selects and returns the night with given nightId.
     * Выбирает и возвращает ночь с вечером я.
     */
    @Query("SELECT * from fact_todo WHERE factId = :id")
    fun getFacttWithId(id: Long): LiveData<Fact>

    @Query("SELECT * FROM fact_todo WHERE paemi = :paemi ORDER BY factId DESC")
    fun getPaemiFacts(paemi: Char): LiveData<List<Fact>>

   // @Insert(onConflict = OnConflictStrategy.REPLACE)
   // suspend fun insertAll(fact: List<Fact>)

}
