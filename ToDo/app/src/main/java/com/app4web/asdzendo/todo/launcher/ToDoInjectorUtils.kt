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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app4web.asdzendo.todo.database.FactDatabase
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import com.app4web.asdzendo.todo.database.FactRepository
import com.app4web.asdzendo.todo.ui.todo.ToDoViewModel
import com.app4web.asdzendo.todo.ui.detail.FactDetailViewModel
import timber.log.Timber

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 * Статические методы используются для введения классов, необходимых для различных действий и фрагментов.
 * Context - надо исправлять
 *   Не стоит передавать Activity в модель в качестве Context. Это может привести к утечкам памяти.
 *   Если вам в модели понадобился объект Context, то вы можете наследовать не ViewModel, а AndroidViewModel.
 */
object ToDoInjectorUtils {

// Fragment <- ViewModel <- ViewModelFactory <- provideToDoActitityViewModelFactory
// <- getFactRepository <- FactRepository <- FactDatabase <- FactDatabaseDao <- @Entity Fact

    // Вызывает создание/восстановление ссылки на этот репозиторий с привязкой FactDatabaseDao
    private fun getFactRepository(context: Context): FactRepository =
            FactRepo.getInstance(
                    FactDatabase.getInstance(context.applicationContext).factDatabaseDao)

    object FactRepo{    // For Singleton instantiation
        @Volatile private var instance: FactRepository? = null
        fun getInstance(factDao: FactDatabaseDao) =
                instance ?: synchronized(this) {
                    instance ?: FactRepository(factDao).also { instance = it }
                }
    }

    fun provideToDoActitityViewModelFactory(context: Context): ToDoActivityViewModelFactory =
        ToDoActivityViewModelFactory(getFactRepository(context))

    fun provideToDoViewModelFactory(context: Context): ToDoViewModelFactory =
        ToDoViewModelFactory(getFactRepository(context))

    fun provideFactDetailViewModelFactory(context: Context, factId: Int, paemi: PAEMI)
            : FactDetailViewModelFactory =
        FactDetailViewModelFactory(getFactRepository(context), factId, paemi)
}
/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 * Это в значительной степени шаблонный код для фабрики ViewModel.
 */

class ToDoActivityViewModelFactory(
        private val repository: FactRepository,
) : ViewModelProvider.Factory {
    init { Timber.i("ToDoViewModelFactory ask ToDoViewModel ")}

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Timber.i("ToDoViewModelFactory ToDoViewModel created")
        return ToDoActitityViewModel(repository) as T
    }
}

class ToDoViewModelFactory(
        private val repository: FactRepository,
) : ViewModelProvider.Factory {
    init { Timber.i("ToDoViewModelFactory ask ToDoViewModel ")}

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Timber.i("ToDoViewModelFactory ToDoViewModel created")
        return ToDoViewModel(repository) as T
    }
}

class FactDetailViewModelFactory(
        private val factRepository: FactRepository,
        private val factID: Int,
        private val paemi: PAEMI
) : ViewModelProvider.Factory {
    init { Timber.i("ToDo Detail ViewModel Factory ask viewmodel ")}

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Timber.i("ToDo Detail ViewModel Factory ViewModel created")
        return FactDetailViewModel(factRepository, factID, paemi) as T
    }
}
