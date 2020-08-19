package com.app4web.asdzendo.todo.ui.todo


import android.app.Application
import android.view.MenuItem
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import kotlinx.coroutines.*
import timber.log.Timber

class ToDoViewModel(
        val database: FactDatabaseDao,
        application: Application
) : AndroidViewModel(application) {

    private var PAEMI: String = "S"

    init { Timber.i("ToDoViewModel created PAEMI= $PAEMI")}
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этой ViewModel.
     */
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tofact = MutableLiveData<Fact?>()

    private var _facts : MutableLiveData<List<Fact>> =  MutableLiveData<List<Fact>>(database.getAllPAEMIFacts(PAEMI).value)

    var facts: LiveData<List<Fact>> = database.getAllPAEMIFacts(PAEMI) //database.getAllFacts()
       // get() = _facts


    init {
        initializeTofact()
       // _facts = database.getAllFacts() as MutableLiveData<List<Fact>>
    }

    private fun initializeTofact() {
        uiScope.launch {
            tofact.value = getTofactFromDatabase()
        }
    }
    private suspend fun getTofactFromDatabase(): Fact? {
        return withContext(Dispatchers.IO) {
            val fact = database.getTofact()
           /* if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }*/

            fact
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(night: Fact) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    private suspend fun insert(night: Fact) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     * Выполняется при нажатии кнопки Очистить.Вызывается при демонтаже ViewModel.
     * На этом этапе мы хотим отменить все сопрограммы;
     * в противном случае мы имеем дело с процессами, которым некуда возвращаться
     * использование памяти и ресурсов.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //07.4.5 Задача: обрабатывать щелчки элементов
    // Шаг 1: навигация по клику
    // функцию обработчика щелчков.
    fun onFactClicked(id: Long){
      //  _navigateToSleepDataQuality.value = id
    }

    // Определите метод для вызова после завершения навигации приложения
   // fun onSleepDataQualityNavigated() {
   //     _navigateToSleepDataQuality.value = null
   // }


    private val _snackbar: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
            val snackbar: LiveData<Boolean>
                get() = _snackbar
    fun fabClick() {
        _snackbar.value = true
        Timber.i("ToDotimber ToDoFragment Recycler ViewModel fabClick() SnackbarTrue()")
    }
    fun snackbarFalse() {
        _snackbar.value = false
        Timber.i("ToDo ItimberFragment Recycler SnackbarFalse()")
    }

    // подключено надо доделывать вызов из XML
    fun onClickBottomNavView(paemi: MenuItem): Boolean {
        val returnPAEMI = when (paemi.itemId) {
            R.id.idea -> {
                PAEMI = "I"; true
            }
            R.id.plan -> {
                PAEMI = "P"; true
            }
            R.id.action -> {
                PAEMI = "A"; true
            }
            R.id.event -> {
                PAEMI = "E"; true
            }
            R.id.money -> {
                PAEMI = "M"; true
            }
            else -> {
                PAEMI = "S"; false
            }

        }
       // _facts.value = database.getAllPAEMIFacts(PAEMI).value
        _facts.value = database.getAllPAEMIFacts(PAEMI).value
        //facts = _facts
        //_facts = database.getAllPAEMIFacts(PAEMI)
        facts = database.getAllPAEMIFacts(PAEMI)

        return returnPAEMI
    }
    // Вызов в XML стоит но не вызывается
    fun onClickBottomNavView(): Boolean  {
        Timber.i("ToDo OnClick Fragment Recycler OnClick")
        return true
    }
}