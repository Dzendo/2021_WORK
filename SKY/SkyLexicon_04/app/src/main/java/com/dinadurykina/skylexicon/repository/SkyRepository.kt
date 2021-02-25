/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dinadurykina.skylexicon.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dinadurykina.skylexicon.network.Meaning
import com.dinadurykina.skylexicon.network.SkyApi
import com.dinadurykina.skylexicon.network.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository module for handling data operations.
 * Модуль репозитория для обработки операций с данными.
 * вызывается из всех ViewModels сам вызывает SkyApiService функции
 */
//@Singleton - в образце не указывается, кто-то указывает похоже достаточно в Module
class SkyRepository {// Для списка найденных слов расшифрованного
    private val _listWord = MutableLiveData<List<Word>>()  // Содержит все данные
      //  val listWord: LiveData<List<Word>>
      //      get() = _listWord
    suspend fun getSkySearch(slovo: String): LiveData<List<Word>> {
        val rezult: List<Word>
        withContext(Dispatchers.IO) {
            rezult = SkyApi.retrofitService.getSearch(slovo)
        }
        _listWord.value = rezult
        return _listWord
    }

    private val _ListMeaning = MutableLiveData<List<Meaning>>()  // Содержит все данные
       // val ListMeaning: LiveData<List<Meaning>>
       //     get() = _ListMeaning
    suspend fun getSkyMeanings(ids:String): LiveData<List<Meaning>> {  // = SkyApi.retrofitService.getMeanings(ids)
        val rezult: List<Meaning>
        withContext(Dispatchers.IO) {
            rezult = SkyApi.retrofitService.getMeanings(ids)
        }
        _ListMeaning.value = rezult
        return _ListMeaning
    }
}
