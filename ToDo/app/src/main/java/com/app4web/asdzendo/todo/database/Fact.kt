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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.net.URL


/**
 * Represents one night's sleep through start, end times, and the sleep quality.
 * Представляет собой один ночной сон через время начала, окончания и качество сна.
 * Представляет собой одну запись жизни: Идею План Действие
 */
@Entity(tableName = "fact_todo")
data class Fact(
        @PrimaryKey(autoGenerate = true)
        var factId: Long = 0L,
        var data: Long, // LocalDateTime = LocalDateTime.now(),
        var parent: Long,
        var paemi: Char = 'i',
        var nameShort: String,
        var name: String,
        var rezult: String,
        var toWork: Boolean,
        var type: Long, // List<Long>,
        var dataStart: Long, // LocalDate,
        var dataEnd:  Long, //LocalDate,
        var deadLine:  Long, //LocalDate,
        var duration:  Long, //Period,
        var resources: String,
        var money: Int,
        var close: Boolean,
        var defect: String,
      //  var parentList: List<Long>,
      //  var childList: List<Long>,
        var comment: String,
      //  var url: List<URL>
)

/*
        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,

        @ColumnInfo(name = "quality_rating")
        var sleepQuality: Int = -1

 */

