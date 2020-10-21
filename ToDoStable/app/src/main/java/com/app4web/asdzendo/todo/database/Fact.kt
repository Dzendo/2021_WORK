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
import com.app4web.asdzendo.todo.launcher.PAEMI
import java.util.*


/**
 * Represents one record of the fact of life of an idea (I), plan(P), action(A), event(Y), money(M).
 * Представляет собой один запись факта жизни идеи(I), плана(P), действия(A), события(E), денег(M).
 */
//@Fts4           // Полнотекстовый поиск Дает ОШИБКУ
@Entity(tableName = "fact_todo",
        indices = [Index(value = ["paemi", "data", "factId"])])
@TypeConverters(CalendarConverters::class, PaemiConverters::class)
data class Fact(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true)
        var factId: Int = 0,  // пускай нумеруется сам -  никогда не меняется (надо бы не удалять запись 0L)
        @ColumnInfo(index = true)
        var data: Calendar = GregorianCalendar.getInstance(), // System.currentTimeMillis(),  // Сейчас - дата и время создания записи
        var parent: Int = 0, // Какая запись (неважно какого типа) породила эту
        @ColumnInfo(index = true)
        var paemi: PAEMI = PAEMI.N, //PAEMI.N.ordinal,  // идеи(I), плана(P), действия(A), события(E), денег(M) служебная(S)
        var nameShort: String = "", // Факт PAEMI кратко
        var name: String = "",      // Факт PAEMI Полностью
        var rezult: String = "", // ожидаемый или полученный или получившийся РЕЗУЛЬТАТ
        var toWork: Boolean? = false, // закпущена в работу или снята с работы(отказ или удалена)
        var type: Int = 0, // List<Long>, // Номер ссылки в доп справочнике, нужно множественную
        var dataStart: Calendar = GregorianCalendar.getInstance(), //Date.from(LocalDateTime.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        var dataEnd: Calendar = GregorianCalendar.getInstance(), //LocalDate,
        var deadLine: Calendar = GregorianCalendar.getInstance(), // = data, //LocalDate,
        var duration: Long? = dataStart.timeInMillis - dataEnd.timeInMillis,
        var resources: String = "",
        var money: Int = 0,
        var close: Boolean? = false,  // Факт закрыт
        var defect: String = "",  // Халтура недоделки
        //  var parentList: List<Long>,   // Ссылки на родителей надо делать ?
        //  var childList: List<Long>,    // Ссылка на подчиненных надо делать ?
        var comment: String = "",       // просто свободный комментарий для User
        var system: String = "",       // Специальный информация только для меня
        var url: String = "https://developer.android.com/guide", //List<URL>

)

data class FactTable(
        var factId: Int = 0,  // пускай нумеруется сам -  никогда не меняется (надо бы не удалять запись 0L)
        var data: Date? = Date(), // System.currentTimeMillis(),  // Сейчас - дата и время создания записи
        var parent: Long = 0L, // Какая запись (неважно какого типа) породила эту
        var ppaemi: Int = PAEMI.N.ordinal,  // идеи(I), плана(P), действия(A), события(E), денег(M) служебная(S)
        var nameShort: String = "" // Факт PAEMI кратко

)
