package com.app4web.asdzendo.todo.launcher

import androidx.lifecycle.ViewModel
import com.app4web.asdzendo.todo.database.FactRepository
import timber.log.Timber

class ToDoActitityViewModel internal constructor(
        private val factRepository: FactRepository
): ViewModel() {

    init {
        Timber.i("TODOActitityViewModel created")
    }
    // Наблюдается (т.к. это LifeData) из ToDoActivity
    fun count() = factRepository.count()

}