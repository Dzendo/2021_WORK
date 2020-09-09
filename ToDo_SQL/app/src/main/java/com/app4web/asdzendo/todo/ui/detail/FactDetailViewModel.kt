package com.app4web.asdzendo.todo.ui.detail

import androidx.lifecycle.*
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.database.FactRepository
import com.app4web.asdzendo.todo.launcher.PAEMI
import timber.log.Timber


class FactDetailViewModel(
        private val factRepository: FactRepository,
        factID: Int = 0,
        paemi: Int = 0,
): ViewModel() {
    init { Timber.i("TODO FactDetailViewModel created $factID")}

    /**
     * Hold a reference to FactDatabase via its FactDatabaseDao.
     * Держите ссылку на базу данных Fact через ее Fact DatabaseDao.
     * Теперь ссылка factRepository на FactRepository
     */

    /** Coroutine setup variables Переменные настройки сопрограммы
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этой ViewModel.
     */
   // private val viewModelJob = Job()

    private val fact = MediatorLiveData<Fact>()
    fun getFact() = fact


    //val fact1 = getFact().value
    //var paemistring = getFact().value.paemi //PAEMI.values()[(getFact().value!!).paemi].name  // Временно для TextView поменять на адаптер

    init {
        val fact0L: LiveData<Fact> = if (factID == 0)
                MutableLiveData(Fact(paemi = paemi, nameShort = "новый Факт", name = "Факт полностью: новый"))
                 else factRepository.getFactWithId(factID)
        fact.addSource(fact0L, fact::setValue)
        Timber.i("ToDo FactDetailViewModel ${fact0L}")
    }

    init { Timber.i("ToDo FactDetailViewModel  ${getFact().value}")}

    // Добавляет и обрабатывает меню три точки для этого фрагмента
     fun update() {
     //   withContext(Dispatchers.IO) {
         fact.value?.rezult = " Изм ${fact.value?.factId} " + fact.value?.rezult
         factRepository.update(fact.value)
     //   }
         Timber.i("ToDo Detail ViewModel update ${fact.value?.factId} ")
         backupTrue()
     }

     fun insert()  {
        // viewModelScope.launch {    // coroutine стоит в репозитории, наверно здесь не надо???
             //   withContext(Dispatchers.IO) {
         fact.value?.rezult = " Доб " + fact.value?.rezult
         factRepository.insert(fact.value)
             //   }
         //}

        Timber.i("ToDo Detail ViewModel insert ${fact.value?.rezult}")
         backupTrue()
     }

     fun delete() {
      //  withContext(Dispatchers.IO) {
         factRepository.delete(fact.value)
      //  }
         Timber.i("ToDo Detail ViewModel delete ${fact.value?.factId}")
         backupTrue()
     }

   /* override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }*/
    private val _backup: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>(null)
    val backup: LiveData<Boolean?>
        get() = _backup

    private fun backupTrue() {
        _backup.value = true
    }
    fun backupNull() {
        _backup.value = null
    }

}