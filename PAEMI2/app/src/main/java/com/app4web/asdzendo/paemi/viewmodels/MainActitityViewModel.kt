package com.app4web.asdzendo.paemi.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class MainActitityViewModel: ViewModel() {
    init { Timber.i("PAEMItimber MainActitityViewModel")}
    private val _snackbar: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
            val snackbar: LiveData<Boolean>
                get() = _snackbar
    fun fabClick() {
        _snackbar.value = true
        Timber.i("PAEMItimber MainActitityViewModel fabClick() SnackbarTrue()")
    }
    fun snackbarFalse() {
        _snackbar.value = false
        Timber.i("PAEMItimber SnackbarFalse()")
    }

}