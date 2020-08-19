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
 */

package com.app4web.asdzendo.todo.ui.todo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import timber.log.Timber


/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 * Это в значительной степени шаблонный код для фабрики ViewModel.
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 * Предоставляет SleepDatabaseDao и контекст для ViewModel.
 */
class ToDoViewModelFactory(
        private val dataSource: FactDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {
    init { Timber.i("ToDoViewModelFactory ask ToDoViewModel ")}
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
            Timber.i("ToDoViewModelFactory ToDoViewModel created")
            return ToDoViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

