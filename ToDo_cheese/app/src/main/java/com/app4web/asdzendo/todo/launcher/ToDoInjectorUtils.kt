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

package com.app4web.asdzendo.todo.launcher

import android.content.Context
import com.app4web.asdzendo.todo.database.FactDatabase
import com.app4web.asdzendo.todo.database.FactRepository
import com.app4web.asdzendo.todo.ui.detail.FactDetailViewModelFactory
import com.app4web.asdzendo.todo.ui.todo.ToDoViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 * Статические методы используются для введения классов, необходимых для различных действий и фрагментов.
 * Context
 *   Не стоит передавать Activity в модель в качестве Context. Это может привести к утечкам памяти.
 *   Если вам в модели понадобился объект Context, то вы можете наследовать не ViewModel, а AndroidViewModel.

 */
object ToDoInjectorUtils {

// Fragment <- ViewModel <- ViewModelFactory <- provideToDoActitityViewModelFactory
// <- getFactRepository <- FactRepository <- FactDatabase <- FactDatabaseDao <- @Entity Fact

    private fun getFactRepository(context: Context): FactRepository =
        FactRepository.getInstance(
                FactDatabase.getInstance(context.applicationContext).factDatabaseDao)

    fun provideToDoActitityViewModelFactory(context: Context): ToDoActivityViewModelFactory =
        ToDoActivityViewModelFactory(getFactRepository(context))

    fun provideToDoViewModelFactory(context: Context): ToDoViewModelFactory =
        ToDoViewModelFactory(getFactRepository(context))

    fun provideFactDetailViewModelFactory(context: Context, factId: Int, paemi: String): FactDetailViewModelFactory =
        FactDetailViewModelFactory(getFactRepository(context), factId, paemi)
}
