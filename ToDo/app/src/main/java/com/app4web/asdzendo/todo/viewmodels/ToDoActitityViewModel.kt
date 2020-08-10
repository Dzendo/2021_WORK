package com.app4web.asdzendo.todo.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ToDoActitityViewModel: ViewModel() {
    init { Timber.i("ToDoItimber TODOActitityViewModel")}
    private val _snackbar: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
            val snackbar: LiveData<Boolean>
                get() = _snackbar
    fun fabClick() {
        _snackbar.value = true
        Timber.i("ToDotimber ToDoActitityViewModel fabClick() SnackbarTrue()")
    }
    fun snackbarFalse() {
        _snackbar.value = false
        Timber.i("ToDoItimber SnackbarFalse()")
    }

}