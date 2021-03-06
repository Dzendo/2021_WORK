/*
 * Copyright 2018, The Android Open Source Project
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
// https://classroom.udacity.com/courses/ud9012/lessons/2be0ed85-721d-4a8d-a484-909b5c98336c/concepts/b36af08e-1bce-48e8-9a31-2e268907d2f0
package com.dinadurykina.skylexicon.network
// Это файл для хранения сетевого уровня API, который будет использовать наша модель ViewModel представления

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// 8.1 В app/build.gradle добавление зависимостей библиотеки Retrofit.

// SkyApiService Класс содержит слой сети для приложения; то есть это API,
// который вы ViewModel будете использовать для связи с веб-сервисом.
// Это класс, в котором вы будете реализовывать API службы Retrofit.
// 8.2 Мы предоставили переменную для корневого веб-адреса конечной точки сервера Mars:
//private const val BASE_URL = "https://mars.udacity.com/"
//private const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"
// https://dictionary.skyeng.ru/api/public/v1/words/search?search=TABLE
// https://dictionary.skyeng.ru/api/public/v1/meanings?ids=109140
private const val BASE_URL = "https://dictionary.skyeng.ru/"

// 16.1 определяет константы для соответствия значениям запроса, которые ожидает наш веб-сервис
//enum class SkyApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }
// 8.8.4 используйте Moshi Builder для создания объекта Moshi с KotlinJsonAdapterFactory:
// преобразует Json с полями в объекты Kotlin data class c с полями объектов
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// 8.3 Используйте Retrofit.Builder для создания объекта Retrofit.
private val retrofit = Retrofit.Builder()
    //.addConverterFactory(ScalarsConverterFactory.create())    // Выдает непреобразованный Json
    .addConverterFactory(MoshiConverterFactory.create(moshi))  // 8.8.5 измените с нашим moshi Object:
    //.addCallAdapterFactory(CoroutineCallAdapterFactory()) // 8.9.2 добавьте к Retrofit строителю
    .baseUrl(BASE_URL)
    .build()
// 8.4 Создайте MarsApiService интерфейс и определите getProperties() метод для запроса строки ответа JSON.
// Аннотируйте метод с помощью @GET, указав конечную точку для ответа по skyeng JSON,
// и создайте Call объект Retrofit , который запустит HTTP-запрос.
// В Основном через него приложение и обращается через API к интернету а он возращает ответ через фабрику конверторы
// Это аналогично интерфейсу DAO для ROOM типа концептуально того же
interface SkyApiService {

    @GET("api/public/v1/words/search")
    suspend fun getSearch(@Query("search") type: String): List<Word>
 /*   fun getSearch(@Query("search") type: String):
// Deferred - штатная функция коруимн котлина обладающая await - т.е приостановкой потока для обработки результата try / cath            
//            Deferred<List<Word>> // 8.9.3 Изменить на Deferred список MarsProperty: для корутин
// Если вверху добавили фабрику из пакета корутин RetroFit то теперь можно здесь убрать обратный вызов и 
// естественно переделывать вызов интерфейса MarsApiService из ViewModel на "корутинный"   8,9,...  
            Call<List<Word>> // 8.8.6 возвращал список MarsProperty объектов вместо String
            //Call<String>
*/
    @GET("api/public/v1/meanings")
   suspend fun getMeanings(@Query("ids") type: String): List<Meaning>
   /* fun getMeanings(@Query("ids") type: String):
// 16.2 @Query("filter") параметр, getProperties() чтобы мы могли фильтровать свойства на основе MarsApiFilter значений перечисления
//    fun getProperties(@Query("filter") type: String):
// Deferred - штатная функция коруимн котлина обладающая await - т.е приостановкой потока для обработки результата try / cath
//            Deferred<List<Word>> // 8.9.3 Изменить на Deferred список MarsProperty: для корутин
// Если вверху добавили фабрику из пакета корутин RetroFit то теперь можно здесь убрать обратный вызов и
// естественно переделывать вызов интерфейса MarsApiService из ViewModel на "корутинный"   8,9,...
    Call<List<Meaning>> // 8.8.6 возвращал список MarsProperty объектов вместо String
     //       Call<String>

    */
}

//8.5 Передав API сервиса, который вы только что определили,
// создайте публичный объект, который называется MarsApi
// для предоставления Retrofit сервиса остальной части приложения:
object SkyApi {
    val retrofitService : SkyApiService by lazy { retrofit.create(SkyApiService::class.java) }
    // Метод Retrofit create()создает сервис Retrofit с MarsApiService интерфейсом
}
// Так здесь создали объект MarsApi для обращения от программы и запроса функций
// Внутри этого объекта встроены: (RetroFit  + (URL-> Конверторы)) + Интерфейс

// Здесь только создан мехпнизм для обращения но использование его в ViewModel
// 8.6 В OverviewViewModel.kt, используйте, MarsApi.retrofitService
// чтобы поставить в очередь запрос Retrofit getMarsRealEstateProperties(),
// переопределяя требуемые обратные вызовы Retrofit,
// чтобы назначить ответ JSON или сообщение об ошибке значению _response LiveData.
// Убедитесь в том , чтобы импортировать ДООСНАСТКУ версии Callback, Call и Response.
//8.7 Не забудьте удалить местозаполнитель присвоения ответа, иначе вы не увидите свои результаты!
// 8.8 Наконец, AndroidManifest.xml добавьте в Интернет разрешение с uses-permission тегом внутри корневого manifest тега.
// 8.9 Создайте и запустите свое приложение, и вы увидите ответ JSON в виде большой строки, которая заполняет весь экран.
// Поэтому соединение с веб-сервисом работает, и мы можем продолжать делать более интересные вещи с нашим приложением.

