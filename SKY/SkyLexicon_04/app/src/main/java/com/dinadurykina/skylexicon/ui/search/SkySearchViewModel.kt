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

package com.dinadurykina.skylexicon.ui.search

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinadurykina.skylexicon.network.Meaning2
import com.dinadurykina.skylexicon.network.Word
import com.dinadurykina.skylexicon.repository.SkyRepository
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class SkySearchViewModel(val slovo:String) : ViewModel() {
    private val skyRepository = SkyRepository()
   // Для Json нерасшифрованного (отладка)
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    // Для одного найденного слова расшифрованного
    private val _word = MutableLiveData<Word>()  // Данные для одного изображения
    val word: LiveData<Word>
        get() = _word
    // Для списка найденных слов расшифрованного
    private val _words = MutableLiveData<List<Word>>()  // Содержит все данные
    val words: LiveData<List<Word>>
        get() = _words

    lateinit var oneWord0: Word

    val meanings02: MutableList<String?> = arrayListOf()
    val meanings20: MutableList<String?> = arrayListOf()

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

     fun onSlovoClicked(view:View) {
         val slovo = (view as EditText).text.toString()
         searchSlovo(slovo)
     }
    fun searchSlovo(slovo:String) {
        viewModelScope.launch {
            _response.value = "empty"
            meanings02.clear()
            meanings20.clear()
            try {
                val skyResult = skyRepository.getSkySearch(slovo)
                _response.value = "Search ${skyResult.value?.size} : \n ${skyResult.value} \n End Sky Search \n "
                _words.value = skyResult.value
                if (skyResult.value?.size?:0 > 0) {
                    _word.value = skyResult.value?.get(0)
                    oneWord0 = skyResult.value?.get(0)!!
                    meanings02.addAll(oneWord0.meanings
                        .map{it.id.toString() + " " + it.translation.text + " " +
                               (it.translation.note ?: "") + " " + it.transcription
                            })
                    meanings20.addAll(words.value!!
                        .map{it.text + " " + it.meanings[0].id.toString() + " " + it.meanings[0].translation.text + " " +
                                (it.meanings[0].translation.note ?: "") + " " + it.meanings[0].transcription
                        })

                }
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
            refreshTrue()
        }
     }
}
