package com.app4web.asdzendo.todo.viewmodels


import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app4web.asdzendo.todo.R
import timber.log.Timber

class FragmentRecyclerViewModel: ViewModel() {
    private var PAEMI: Char = ' '
    init { Timber.i("ToDoItimber TODO Fragment Recycler ViewModel")}
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
    fun onClickBottomNavView(paemi: MenuItem): Boolean =
        when(paemi.itemId) {
            R.id.idea -> { PAEMI = 'I'; true }
            R.id.plan -> { PAEMI = 'P'; true }
            R.id.action -> { PAEMI = 'A'; true }
            R.id.event -> { PAEMI = 'E'; true }
            R.id.money -> { PAEMI = 'M'; true }
            else -> {PAEMI = ' ' ; false }
        }
    // не вызывается
    fun onClickBottomNavView(): Boolean  {
        Timber.i("ToDo OnClick Fragment Recycler OnClick")
        return true
    }
}