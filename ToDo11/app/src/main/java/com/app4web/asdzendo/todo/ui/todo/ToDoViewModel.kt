package com.app4web.asdzendo.todo.ui.todo


import android.view.MenuItem
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.database.FactRepository
import com.app4web.asdzendo.todo.launcher.PAEMI
import com.app4web.asdzendo.todo.launcher.PAEMI.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber


class ToDoViewModel internal constructor(
    private val factRepository: FactRepository
) : ViewModel() {

    val paemi: MutableLiveData<PAEMI> = MutableLiveData<PAEMI>(R)

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     * задание viewModel позволяет нам отменить все сопрограммы, запущенные этой ViewModel.
     * Любая сопрограмма, запущенная в этой области, автоматически отменяется, если ViewModel очищается
     */
    // init {
    var viewModelJob = Job()
    var toDoViewModelJob: Job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    // Coroutine that will be canceled when the ViewModel is cleared.

    //Timber.i("ToDoViewModel created PAEMI= ${paemi.value?.name}")
    //}

    private val factsPage: Flow<PagingData<Fact>>
        get() = factRepository.getAllPageCollect(paemi.value ?: N).flow.cachedIn(viewModelScope)

    /* var factsPage :  Flow<PagingData<Fact>>
        get()  {
            //var gett: Flow<PagingData<Fact>> = flowOf()
            viewModelScope.launch {
                factsPage = factRepository.getAllPageCollect(paemi.value ?: N)
            }
            return factsPage
        }
        set(value){ factRepository.getAllPageCollect(paemi.value ?: N) }
    */

    val adapterPage = ToDoPageAdapter(FactListener { factID -> onFactClicked(factID) })

    // Подпишите адаптер на ViewModel, чтобы элементы в адаптере обновлялись
    // когда список меняется Cancel не работает
    //@OptIn(ExperimentalCoroutinesApi::class)
    fun factsPageChange() {
         // viewModelJob.cancel()
         // viewModelScope.launch {  // viewModelJob = Не транслируется as CompletableJob но работает
             toDoViewModelJob.cancel()   // Показывает  не отменяет
             toDoViewModelJob = uiScope.launch {  // Показывает не отменяет

            factsPage.cancellable().collectLatest {
                adapterPage.submitData(it)
            }
        } //as CompletableJob
    }

    //07.4.5 Задача: обрабатывать щелчки элементов
    private val _navigateToFactDetail = MutableLiveData<Int?>()
    val navigateToFactDetail
        get() = _navigateToFactDetail

    // Шаг 1: навигация по клику
    // функцию обработчика щелчков.
    private fun onFactClicked(factid: Int) {
        _navigateToFactDetail.value = factid
    }

    // Определите метод для вызова после завершения навигации приложения
    fun navigateToFactDetailNavigated() {
        _navigateToFactDetail.value = null
    }

    // подключено  вызов из XML
    fun fabClick() {
        _navigateToFactDetail.value = 0
        Timber.i("ToDotimber ToDoFragment Recycler ViewModel fabClick() SnackbarTrue() ${paemi.value?.name}")
    }

    // подключено  вызов из XML
    fun onClickBottomNavView(clickpaemi: MenuItem): Boolean {
        val oldPaemi = paemi.value
        paemi.value = PAEMI.valueOf(clickpaemi.title.first().toString())

        if (oldPaemi == paemi.value) paemi.value = N
        Timber.i("ToDoViewModel onClickBottomNavView ${paemi.value?.name}}")
        return true
    }

    // Добавляет и обрабатывает пункт меню три точки clear для этого фрагмента
    fun clear() = viewModelScope.launch { factRepository.clear() }
    // Добавляет и обрабатывает пункт меню три точки добавить пачку для этого фрагмента
    fun addFactDatabase(COUNTSFact: Int) = viewModelScope.launch { factRepository.addFactDatabase(COUNTSFact) }

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

}
