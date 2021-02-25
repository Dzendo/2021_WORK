/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.dinadurykina.skylexicon.ui.meanings

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinadurykina.skylexicon.network.Meaning
import com.dinadurykina.skylexicon.repository.SkyRepository
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class SkyMeaningsViewModel(val ids:String) : ViewModel() {
     private val skyRepository = SkyRepository()
    // Для Json нерасшифрованного (отладка)
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    // Для одного значения слова расшифрованного
    private val _meaning = MutableLiveData<Meaning>()  // Данные для одного значения
    val meaning: LiveData<Meaning>
        get() = _meaning
    // Для списка значений слова расшифрованного
    private val _meanings = MutableLiveData<List<Meaning>>()  // Содержит все данные
    val meanings: LiveData<List<Meaning>>
        get() = _meanings

    lateinit var oneMeanig: Meaning

    val examples: MutableList<String?> =  arrayListOf()
    val meaningsWithSimilarTranslation: MutableList<String?> =  arrayListOf()
    val alternativeTranslations: MutableList<String?> =  arrayListOf()
    val images: MutableList<String?> =  arrayListOf()

    // Объявляю живой флажок, надо ли обновить адапреты ListView из фрагмента, (по умолчанию нет - null)
    private val _refresh: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>(null)
    val refresh: LiveData<Boolean?>
        get() = _refresh
    private fun refreshTrue() {
        _refresh.value = true
    }
    fun refreshNull() {
        _refresh.value = null
    }

    init {
   //     meaningsIds(ids)
    }
    /**
     * Sets the value of the status LiveData to the Sky API status.
     */
     fun onIdsClicked(view:View) {
        val ids = (view as EditText).text.toString()
        meaningsIds(ids)
    }
     fun meaningsIds(ids: String) {
         viewModelScope.launch {
             _response.value = "empty"
             //_meaning.value = Meaning()
             // oneMeanig =
             examples.clear()
             meaningsWithSimilarTranslation.clear()
             alternativeTranslations.clear()
             images.clear()
             try {
                 val skyResult = skyRepository.getSkyMeanings(ids)
                 _response.value = "Meanings ${skyResult.value?.size} : \n ${skyResult.value} \n End Sky Meanings  \n"
                 _meanings.value = skyResult.value
                 if (skyResult.value?.size?:0 > 0) {
                     _meaning.value = skyResult.value?.get(0)
                     oneMeanig = skyResult.value?.get(0)!!

                     examples.addAll( oneMeanig.examples.map{it.text})
                     meaningsWithSimilarTranslation.addAll( oneMeanig.meaningsWithSimilarTranslation
                         .map{it.meaningId.toString() + " " + it.translation.text + " " +
                                 (it.translation.note ?: "") + " " +
                                 it.partOfSpeechAbbreviation + it.frequencyPercent + "%"
                         })
                     alternativeTranslations.addAll( oneMeanig.alternativeTranslations
                         .map{it.text + " " + it.translation.text + " " +
                                 (it.translation.note ?: "")
                         })

                     images.addAll( oneMeanig.images.map{it.url})
                 }
             } catch (e: Exception) {
                 _response.value = "Failure: ${e.message}"
             }
             refreshTrue()    // notifyDataSetChanged()
         }
    }
}
