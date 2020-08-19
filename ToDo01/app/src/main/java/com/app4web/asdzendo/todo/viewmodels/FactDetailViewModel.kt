package com.app4web.asdzendo.todo.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import com.app4web.asdzendo.todo.ui.ToDoApplication.Companion.FactContentFACTS
import kotlinx.coroutines.Job
import timber.log.Timber

class FactDetailViewModel(
    factID: Long = 1L,
    dataSource: FactDatabaseDao): ViewModel() {

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

    private val fact : MutableLiveData<Fact> = MutableLiveData<Fact>()

    fun getFact() = fact

    init {
        //fact.addSource(LiveData(Fact.FACTS[1]), fact::setValue)
        fact.value = FactContentFACTS[factID.toInt()]
       // fact.addSource(database.getFactWithId(factID), fact::setValue)
        // night=database.getNightWithId(sleepNightKey)

    }

    private var PAEMI: Char = ' '
    init { Timber.i("ToDoItimber TODO Fragment Recycler ViewModel")}
    private val _snackbar: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
            val snackbar: LiveData<Boolean>
                get() = _snackbar

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}