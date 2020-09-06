package com.app4web.asdzendo.todo.ui.todo

import android.view.MenuItem
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.database.FactRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class ToDoViewModel internal constructor(
    private val factRepository: FactRepository
) : ViewModel() {

    val PAEMI: MutableLiveData<String> = MutableLiveData<String>("Z")

    init { Timber.i("ToDoViewModel created PAEMI= ${PAEMI.value}")}
    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этой ViewModel.
     */
   // private val viewModelJob = Job()
   // private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val factsPage :  Flow<PagingData<Fact>>
        get() = //Pager( PagingConfig(  pageSize = 60, enablePlaceholders = true, maxSize = 200 )){factRepository.getAllPage()}.flow
             when (PAEMI.value) {
                "Z" -> { Pager( PagingConfig(  pageSize = 70, enablePlaceholders = true, maxSize = 210 )){factRepository.getAllPage()}.flow}
                " " -> Pager( PagingConfig(  pageSize = 70, enablePlaceholders = true, maxSize = 210 )){factRepository.getAllFactsPage()}.flow
                else -> Pager( PagingConfig(  pageSize = 70, enablePlaceholders = true, maxSize = 210 )){factRepository.getAllPAEMIFactsPage(PAEMI.value)}.flow
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
   /* override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }*/

    //07.4.5 Задача: обрабатывать щелчки элементов
    private val _navigateToFactDetail = MutableLiveData<Long>()
    val navigateToFactDetail
        get() = _navigateToFactDetail
    // Шаг 1: навигация по клику
    // функцию обработчика щелчков.
    fun onFactClicked(factid: Long){
        _navigateToFactDetail.value = factid
    }
    // Определите метод для вызова после завершения навигации приложения
    fun navigateToFactDetailNavigated() {
        _navigateToFactDetail.value = null
    }

    fun fabClick() {
        _navigateToFactDetail.value = 0L
        Timber.i("ToDotimber ToDoFragment Recycler ViewModel fabClick() SnackbarTrue() ${PAEMI.value}")
    }

    // подключено надо доделывать вызов из XML
    fun onClickBottomNavView(paemi: MenuItem): Boolean {
        val oldPaemi = PAEMI.value
        PAEMI.value = when (paemi.itemId) {
            R.id.idea ->   "I"
            R.id.plan ->   "P"
            R.id.action -> "A"
            R.id.event ->  "E"
            R.id.money ->  "M"
            else -> "S"
        }
        if (oldPaemi == PAEMI.value) PAEMI.value = " "
        Timber.i("ToDoViewModel onClickBottomNavView ${PAEMI.value}}")
        return true
    }

    // Вызов в XML стоит но не вызывается
    fun onClickBottomNavView(): Boolean  {
        Timber.i("ToDoViewModel onReClickBottomNavView ${PAEMI.value}}")
        return true
    }
    // Добавляет и обрабатывает меню три точки для этого фрагмента
    fun clear() = factRepository.clear()

    fun addFactDatabase(COUNTSFact: Long) = factRepository.addFactDatabase(COUNTSFact)

    fun count() = factRepository.count()
}