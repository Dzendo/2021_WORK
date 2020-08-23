package com.app4web.asdzendo.todo.ui.detail

import androidx.lifecycle.*
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.database.FactRepository
import timber.log.Timber

class FactDetailViewModel(
    private val factRepository: FactRepository,
    factID: Long = 0L,
    ): ViewModel() {
    val factid = factID.toString()  // Временно для TextView поменять на адаптер
    /**
     * Hold a reference to FactDatabase via its FactDatabaseDao.
     * Держите ссылку на базу данных Fact через ее Fact DatabaseDao.
     */
    //val database = dataSource
    /** Coroutine setup variables Переменные настройки сопрограммы
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этой ViewModel.
     */
   // private val viewModelJob = Job()

    //private val fact : MutableLiveData<Fact> = MutableLiveData<Fact>()
    private val fact = MediatorLiveData<Fact>()
    fun getFact() = fact

    init {
        fact.addSource(factRepository.getFactWithId(factID), fact::setValue)
    }

    init { Timber.i("ToDo Detail ViewModel")}

     fun update(fact: Fact?) {
     //   withContext(Dispatchers.IO) {
             fact?.rezult = "Измененный ${fact?.factId}"
            factRepository.update(fact)

     //   }
         Timber.i("ToDo Detail ViewModel update ${fact?.factId}")
         backupTrue()
     }

     fun insert(fact: Fact?)  {
        // viewModelScope.launch {    // coroutine стоит в репозитории наверно здесь не надо???
             //   withContext(Dispatchers.IO) {
             fact?.rezult = "Добавлен ${(0..100).random()}"
             factRepository.insert(fact)
             //   }
         //}

        Timber.i("ToDo Detail ViewModel insert ${fact?.rezult}")
         backupTrue()
     }

     fun delete(fact: Fact?) {
      //  withContext(Dispatchers.IO) {
            factRepository.delete(fact)
      //  }
         Timber.i("ToDo Detail ViewModel delete ${fact?.factId}")
         backupTrue()
     }

   /* override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }*/
    private val _backup: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>(null)
    val backup: LiveData<Boolean?>
        get() = _backup

    fun backupTrue() {
        _backup.value = true
    }
    fun backupNull() {
        _backup.value = null
    }
}