package com.app4web.asdzendo.todo.launcher

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app4web.asdzendo.todo.database.FactRepository
import timber.log.Timber

class ToDoActitityViewModel @ViewModelInject constructor(
       private val factRepository: FactRepository
): ViewModel() {
    // Наблюдается (т.к. это LifeData) из ToDoActivity
    val count:LiveData<Int> = factRepository.count()
    init {
        Timber.i("TODOActitityViewModel created")
    }
}