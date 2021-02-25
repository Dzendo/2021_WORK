package com.dinadurykina.skylexicon.ui.meanings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SkyMeaningsViewModelFactoryDebug (
    private val ids: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(SkyMeaningsViewModelDebug:Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SkyMeaningsViewModelDebug(ids) as T
    }
}

//protected
inline fun <VM : ViewModel> viewModelFactoryDebug(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
    }
/*
binding.authViewModel = ViewModelProviders.of(
  this,
 viewModelFactory { MyViewModel("albert") }
).get(AuthViewModel::class.java)
 */