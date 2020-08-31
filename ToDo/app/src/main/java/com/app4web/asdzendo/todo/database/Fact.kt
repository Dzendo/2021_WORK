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

import androidx.room.*
import java.util.*


/**
 * Represents one record of the fact of life of an idea (I), plan(P), action(A), event(Y), money(M).
 * Представляет собой один запись факта жизни идеи(I), плана(P), действия(A), события(E), денег(M).
 */
//@Fts4           // Полнотекстовый поиск Дает ОШИБКУ
@Entity(tableName = "fact_todo",
        indices = [Index(value = ["data" , "factId"],name = "date_id_index")])
       // indices = @Index(value = {"data" , "factId"},name = "date_id_index"))
@TypeConverters(CharConverters::class, CalendarConverters::class, DateConverters::class)
data class Fact(
        @ColumnInfo(index = true)
        @PrimaryKey(autoGenerate = true)
        var factId: Long = 0L,  // пускай нумеруется сам -  никогда не меняется (надо бы не удалять запись 0L)
        @ColumnInfo(index = true)
        var data: Date? = Date(), // System.currentTimeMillis(),  // Сейчас - дата и время создания записи
        var parent: Long = 0L, // Какая запись (неважно какого типа) породила эту
        @ColumnInfo(index = true)
        var paemi: String? = "S",  // идеи(I), плана(P), действия(A), события(E), денег(M) служебная(S)
        var nameShort: String = "", // Факт PAEMI кратко
        var name: String = "",      // Факт PAEMI Полностью
        var rezult: String = "", // ожидаемый или полученный или получившийся РЕЗУЛЬТАТ
        var toWork: Boolean? = false, // закпущена в работу или снята с работы(отказ или удалена)
        var type: Long = 0L, // List<Long>, // Номер ссылки в доп справочнике, нужно множественную
        var dataStart: Date? = Date(), //Date.from(LocalDateTime.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        var dataEnd: Date? = Date(), //LocalDate,
        var deadLine: Calendar = Calendar.getInstance(), // = data, //LocalDate,
        var duration: Long? = dataStart?.time?.let { dataEnd?.time?.minus(it) },// 0L, //Period,
        var resources: String = "",
        var money: Int = 0,
        var close: Boolean? = false,  // Факт закрыт
        var defect: String = "",  // Халтура недоделки
        //  var parentList: List<Long>,   // Ссылки на родителей надо делать ?
        //  var childList: List<Long>,    // Ссылка на подчиненных надо делать ?
        var comment: String = "",       // просто свободный комментарий для User
        var system: String = "",       // Специальный информация только для меня
        var url: String = "https://developer.android.com/guide"              //List<URL>
)

data class FactTable(
        var factId: Long = 0L,  // пускай нумеруется сам -  никогда не меняется (надо бы не удалять запись 0L)
        var data: Date? = Date(), // System.currentTimeMillis(),  // Сейчас - дата и время создания записи
        var parent: Long = 0L, // Какая запись (неважно какого типа) породила эту
        var paemi: String? = "S",  // идеи(I), плана(P), действия(A), события(E), денег(M) служебная(S)
        var nameShort: String = "" // Факт PAEMI кратко
)
