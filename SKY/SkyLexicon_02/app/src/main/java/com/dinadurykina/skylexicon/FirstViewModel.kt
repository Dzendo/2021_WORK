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

package com.dinadurykina.skylexicon

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinadurykina.skylexicon.network.SkyApi
import com.dinadurykina.skylexicon.network.Word
import kotlinx.coroutines.launch
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class FirstViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    // 11.1.3 Добавьте инкапсулированное LiveData<MarsProperty>свойство:
    private val _property = MutableLiveData<Word>()  // Данные для одного изображения
    val property: LiveData<Word>
        get() = _property
    // 12.1.2  переименованы _property к _properties, и назначьте его List на MarsProperty:
    private val _properties = MutableLiveData<List<Word>>()  // Содержит все данные
    val properties: LiveData<List<Word>>
        get() = _properties

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
       // getSkySearch("Chair")
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
     fun onSlovoClicked(view:View)= getSkySearch((view as EditText).text.toString())
     fun getSkySearch(slovo:String) {
        // 8.7 Не забудьте удалить местозаполнитель присвоения ответа, иначе вы не увидите свои результаты!
        // _response.value = "Set the Mars API Response here!"

        // 8.6 В OverviewViewModel.kt, используйте, MarsApi.retrofitService
        // чтобы поставить в очередь запрос Retrofit getMarsRealEstateProperties(),
        // переопределяя требуемые обратные вызовы Retrofit,
        // чтобы назначить ответ JSON или сообщение об ошибке значению _response LiveData.
        // Убедитесь в том , чтобы импортировать ДООСНАСТКУ версии Callback, Call и Response.

        /*
        Первый вариант без преобразований:
        В OverviewViewModel.kt, используйте, MarsApi.retrofitService
        чтобы поставить в очередь запрос Retrofit getMarsRealEstateProperties(),
        переопределяя требуемые обратные вызовы Retrofit,
        чтобы назначить ответ JSON или сообщение об ошибке значению _response LiveData
         */
        // Метод возвращает объект. Затем вы можете вызвать этот объект, чтобы запустить сетевой запрос в фоновом потоке.
        // 8.8.7 чтобы обрабатывать список MarsProperty вместо String.

        // Use coroutines with Retrofit по Codelabs
         viewModelScope.launch {
             try {
                 val skyResult = SkyApi.retrofitService.getSearch(slovo)
                 _response.value = "Search ${skyResult.size} : \n ${skyResult} End Sky Search \n \n"
                 if (skyResult.size > 0)
                     _property.value = skyResult[0]
             } catch (e: Exception) {
                 _response.value = "Failure: ${e.message}"
             }
         }

         // без coroutines
       /* SkyApi.retrofitService.getSearch(slovo).enqueue( object: Callback<List<Word>> { //<String> {  // Callback - обратный вызов передается в качестве параметра
            override fun onFailure(call: Call<List<Word>>, t: Throwable) {  //<String>, t: Throwable) {
                _response.value = "Failure: " + t.message

            }
            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {  //<String>, response: Response<String>) {
               // _response.value = response.body()  //Mars properties retrieved" //response.body()
               // _response.value = "Success: ${response.body()?.size} Sky properties retrieved"
                _response.value = "Search ${response.body()?.size} : \n ${response.body()?.toString()} End Sky Search \n \n"
            }
        })*/
        // 8.8.8 Создайте и запустите приложение. Вы должны увидеть одно сообщение, показывающее количество свойств в ответе. - 838 участков
        // при вызове из программы этот код поставит ст. объект Callback<String> к себе там в очередь
        // когда MarsApi.retrofitService выполнит getProperties() то он вызовет обратным вызовом
        // при ошибке - onFailure ; при удаче - onResponse ==> здесь то мы и получим ответ
    }
}
