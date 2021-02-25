package com.dinadurykina.skylexicon.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SkySearchViewModelFactory (
    private val slovo: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(SkySearchViewModel:Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SkySearchViewModel(slovo) as T
    }
}

//protected
inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
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