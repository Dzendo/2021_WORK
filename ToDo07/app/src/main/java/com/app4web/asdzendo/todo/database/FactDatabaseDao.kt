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
import androidx.paging.PagingSource
import androidx.room.*
import com.app4web.asdzendo.todo.launcher.PAEMI

/**
 * Defines methods for using the Fact class with Room.
 * Определяет методы для использования класса Fact с Room.
 */
@Dao
@TypeConverters(CalendarConverters::class, PaemiConverters::class)
interface FactDatabaseDao {

    // Справочно: количество строк в таблице
    //  SELECT count(*) FROM employee;
    @Query("SELECT COUNT(*) FROM fact_todo")
    fun getCount(): LiveData<Int>

    /**
     * Добавляет в базу сразу список готовых фактов
     * применяется сечас для заполнения отладочными строками
     * подготовка строк в репозитории
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(facts: List<Fact>): List<Long>

    // стандарт : чтобы был нарастающий ID fact.factid = 0L обязательно или null
    @Insert  // (onConflict = OnConflictStrategy.REPLACE) - будет всавлять или затирать существующую
    suspend fun insert(fact: Fact): Long

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     * При обновлении строки со значением, уже установленным в столбце,
     * заменяет старое значение на новое.
     *
     * @param fact new value to write
     */
    @Update // если не находит заппись то ничего не будет int - количество обновленных записей
    suspend fun update(fact: Fact): Int

    @Delete  // если нет записи - ошибка int - кол-во удаленных записей
    suspend fun delete(fact: Fact): Int

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     * Выбирает и возвращает строку, которая соответствует предоставленному id  что является ключом.
     *
     * @param id startTimeMilli to match
     */
    @Query("SELECT * from fact_todo WHERE factId = :id")
    fun get(id: Int): Fact?

    /**
     * Deletes all values from the table.
     * Удаляет все значения из таблицы.
     *
     * This does not delete the table, only its contents.
     * При этом таблица не удаляется, а только ее содержимое.
     */
    @Query("DELETE FROM fact_todo")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *Выбирает и возвращает все строки в таблице,
     * sorted by factid in descending order.
     * сортировка по factid в порядке убывания.
     */
    @Query("SELECT * FROM fact_todo LIMIT 700")
    fun getAll(): LiveData<List<Fact>>

    @Query("SELECT * FROM fact_todo ORDER BY factId DESC")
    fun getAllPage(): PagingSource<Int, Fact>

    @Query("SELECT * FROM fact_todo  ORDER BY data DESC LIMIT 700")
    fun getAllFacts(): List<Fact>

    @Query("SELECT * FROM fact_todo ORDER BY data ASC")
    fun getAllFactsPage():PagingSource<Int, Fact>

    /**
     * Selects and returns the latest night.
     * Выбирает и возвращает последнюю запись.
     * не используется - надо для восстановления Recycler
     */
    @Query("SELECT * FROM fact_todo ORDER BY factId DESC LIMIT 1")
    fun getTofact(): Fact?

    /**
     * Selects and returns the night with given nightId.
     * Выбирает и возвращает fact c id.
     */
    @Query("SELECT * from fact_todo WHERE factId = :id")
    fun getFactWithId(id: Int): LiveData<Fact>

    /**
     * выборка фильтром PAEMI в обратной сортировке
     */
    @Query("SELECT * FROM fact_todo WHERE paemi = :paemi ORDER BY factId DESC LIMIT 700")
    fun getAllPAEMIFactsID(paemi: Int): LiveData<List<Fact>>

    /**
     * выборка фильтром PAEMI в обратной сортировке
     */

    @Query("SELECT * FROM fact_todo WHERE paemi = :paemi ORDER BY data DESC, factId DESC LIMIT 700")
    fun getAllPAEMIFacts(paemi: Int): LiveData<List<Fact>>

    @Query("SELECT * FROM fact_todo WHERE paemi = :paemi AND data BETWEEN :FilterDateStart AND :FilterDateEnd ORDER BY data DESC, factId DESC")
    fun getAllPAEMIFactsPage(paemi: PAEMI,FilterDateStart: Long, FilterDateEnd:Long):  PagingSource<Int, Fact>

    /**
     * выборка фильтром PAEMI по индексу в обратной сортировке
     */
    @Query("SELECT * FROM fact_todo WHERE paemi = :paemi ORDER BY data DESC, factId DESC LIMIT 700")
    fun getAllPAEMIFactsIndex(paemi: PAEMI): LiveData<List<Fact>>
}

@Dao
interface FactTableDao {
    @Query("SELECT factId, data, parent, paemi, nameShort FROM fact_todo")
    fun getFactTable(): LiveData<List<FactTable>>

    @Query("SELECT factId, data, parent, paemi, nameShort FROM fact_todo ORDER BY factId DESC")
    fun getAllFacts(): LiveData<List<FactTable>>

    /**
     * выборка фильтром PAEMI в обратной сортировке
     */
    @Query("SELECT factId, data, parent, paemi, nameShort FROM fact_todo WHERE paemi = :paemi ORDER BY factId DESC")
    fun getAllPAEMIFacts(paemi: Int): LiveData<List<FactTable>>
}
