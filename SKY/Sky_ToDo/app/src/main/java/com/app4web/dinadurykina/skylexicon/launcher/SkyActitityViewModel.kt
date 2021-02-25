package com.app4web.dinadurykina.skylexicon.launcher

//import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app4web.dinadurykina.skylexicon.database.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SkyActitityViewModel @Inject constructor(
       private val factRepository: FactRepository
): ViewModel() {
    // Наблюдается (т.к. это LifeData) из SkyActivity
    val count:LiveData<Int> = factRepository.count()
    init {
        Timber.i("TODOActitityViewModel created")
    }
}