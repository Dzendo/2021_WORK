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
class SkyMeaningsViewModel : ViewModel() {
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
    val properties: LiveData<List<Meaning>>
        get() = _meanings

    /**
     * Sets the value of the status LiveData to the Sky API status.
     */
     fun onIdsClicked(view:View) {
        val ids = (view as EditText).text.toString()
         viewModelScope.launch {
             try {
                 val skyResult = skyRepository.getSkyMeanings(ids)
                 _response.value = "Search ${skyResult.size} : \n ${skyResult} \n End Sky Search  \n"
                 if (skyResult.isNotEmpty())
                     _meaning.value = skyResult[0]
             } catch (e: Exception) {
                 _response.value = "Failure: ${e.message}"
             }
         }
    }
}
