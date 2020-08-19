package com.app4web.asdzendo.todo.ui.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import kotlinx.coroutines.Job
import timber.log.Timber

class FactDetailViewModel(
    factID: Long = 0L,
    dataSource: FactDatabaseDao): ViewModel() {
    val factid = factID.toString()  // Временно для TextView поменять на адаптер
    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     * Держите ссылку на базу данных Sleep через ее Sleep DatabaseDao.
     */
    val database = dataSource
    /** Coroutine setup variables Переменные настройки сопрограммы */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этой ViewModel.
     */
    private val viewModelJob = Job()

    //private val fact : MutableLiveData<Fact> = MutableLiveData<Fact>()
    private val fact = MediatorLiveData<Fact>()
    fun getFact() = fact

    init {
        fact.addSource(database.getFactWithId(factID), fact::setValue)
    }

    init { Timber.i("ToDo Detail ViewModel")}

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}