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

package com.dinadurykina.skylexicon.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
//import kotlinx.android.parcel.Parcelize

// преобразовать класс в класс данных Kotlin со свойствами, которые соответствуют полям ответа JSON
// Moshi анализирует эти данные JSON и преобразует их в объекты Kotlin.
// Для этого ему необходим класс данных Kotlin для хранения проанализированных результатов,
// поэтому следующим шагом будет создание этого класса.
// 8.8.2 преобразовать класс в класс данных Kotlin со свойствами, которые соответствуют полям ответа JSON
// 8.8.3 Переименуйте свойство класса img_src в imgSrcUrl и добавьте аннотацию @Json, чтобы переназначить в него поле JSON img_src:
// 14 Добавить : @Parcelize  и Parcelable  ( id 'kotlin-android-extensions') experimental = true - Без кучи методов
// 15 добавление подробного экрана
// 15.4.1 сделайте класс доступным, расширив его Parcelable и добавив @Parcelize аннотацию:
@Parcelize
data class SkyMeaningsAnswer(
    val id: String,
    @Json(name = "img_src")  // это название на сайте (в Json)
    val imgSrcUrl: String,   // это название для нас использовать
    val type: String,
    val price: Double) : Parcelable {     // все числовые (без кавычек) объявлять Double
// 15.6.1  isRental логическое значение и установите его значение в зависимости от того, является ли тип свойства «арендным»
    val isRental
        get() = type == "rent"
}
// Порядок полей не совпадает с порядеом на сайте - разбор по именам
/*см. src\main\assets
там данные о собсвенности на марсе, кот будем считывать

[{"price":450000,    // цена собственности на Марс, как число.
    "id":"424906",              // идентификатор свойства в виде строки.
    "type":"rent",              // или "rent"или "buy".
    "img_src":"http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg"}, // URL изображения в виде строки.
},
...
{
    "price":8000000,
    "id":"424908",
    "type":"rent",
    "img_src": "http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631290305226E03_DXXX.jpg"
}]
*/
/*
[
  {
    "id": "1938",
    "wordId": 838,
    "difficultyLevel": 1,
    "partOfSpeechCode": "n",
    "prefix": "a",
    "text": "chair",
    "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=chair",
    "transcription": "К§eЙ™",
    "properties": {
      "collocation": false,
      "countability": "c",
      "irregularPlural": false,
      "falseFriends": []
    },
    "updatedAt": "2021-02-10 04:49:35",
    "mnemonics": null,
    "translation": {
      "text": "СЃС‚СѓР»",
      "note": null
    },
    "images": [
      {
        "url": "//d2zkmv5t5kao9.cloudfront.net/images/b905a618b56c721ce683164259ac02c4.jpeg?w=200&h=150&q=50"
      }
    ],
    "definition": {
      "text": "A separate seat for one person, with four legs and a support for the back.",
      "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=a+separate+seat+for+one+person+with+four+legs+and+a+support+for+the+back"
    },
    "examples": [
      {
        "text": "Put an additional [chair], please.",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=Put+an+additional+chair+please."
      },
      {
        "text": "He put his coat over the back of the [chair] and sat down.",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=He+put+his+coat+over+the+back+of+the+chair+and+sat+down."
      },
      {
        "text": "Don't tip your [chair] back like that, you'll fall.",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=Don%27t+tip+your+chair+back+like+that+you%27ll+fall."
      },
      {
        "text": "Put [chairs] round the table, please.",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=Put+chairs+round+the+table+please."
      },
      {
        "text": "I had to sit on a hard wooden [chair] all day.",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=I+had+to+sit+on+a+hard+wooden+chair+all+day."
      }
    ],
    "meaningsWithSimilarTranslation": [
      {
        "meaningId": 1938,
        "frequencyPercent": "50.0",
        "partOfSpeechAbbreviation": "СЃСѓС‰.",
        "translation": {
          "text": "СЃС‚СѓР»",
          "note": null
        }
      },
      {
        "meaningId": 1940,
        "frequencyPercent": "20.0",
        "partOfSpeechAbbreviation": "СЃСѓС‰.",
        "translation": {
          "text": "РїСЂРµРґСЃРµРґР°С‚РµР»СЊ",
          "note": null
        }
      },
      {
        "meaningId": 1939,
        "frequencyPercent": "15.0",
        "partOfSpeechAbbreviation": "СЃСѓС‰.",
        "translation": {
          "text": "РґРѕР»Р¶РЅРѕСЃС‚СЊ РїСЂРѕС„РµСЃСЃРѕСЂР°",
          "note": ""
        }
      },
      {
        "meaningId": 1941,
        "frequencyPercent": "10.0",
        "partOfSpeechAbbreviation": "СЃСѓС‰.",
        "translation": {
          "text": "СЌР»РµРєС‚СЂРёС‡РµСЃРєРёР№ СЃС‚СѓР»",
          "note": ""
        }
      },
      {
        "meaningId": 1942,
        "frequencyPercent": "5.0",
        "partOfSpeechAbbreviation": "СЃСѓС‰.",
        "translation": {
          "text": "РјРµСЃС‚Рѕ",
          "note": "РІ РѕСЂРєРµСЃС‚СЂРµ"
        }
      },
      {
        "meaningId": 1943,
        "frequencyPercent": "0.0",
        "partOfSpeechAbbreviation": "РіР».",
        "translation": {
          "text": "СЂСѓРєРѕРІРѕРґРёС‚СЊ",
          "note": "С‡РµРј-Р»РёР±Рѕ"
        }
      }
    ],
    "alternativeTranslations": [
      {
        "text": "company",
        "translation": {
          "text": "С‚СЂСѓРїРїР°",
          "note": ""
        }
      },
      {
        "text": "frog",
        "translation": {
          "text": "Р»СЏРіСѓС€РєР°",
          "note": null
        }
      },
      {
        "text": "canyon",
        "translation": {
          "text": "РєР°РЅСЊРѕРЅ",
          "note": null
        }
      },
      {
        "text": "daddy",
        "translation": {
          "text": "РїР°РїРѕС‡РєР°",
          "note": null
        }
      },
      {
        "text": "lamb",
        "translation": {
          "text": "СЏРіРЅС‘РЅРѕРє",
          "note": null
        }
      },
      {
        "text": "trunk",
        "translation": {
          "text": "РєРѕС„СЂ",
          "note": ""
        }
      },
      {
        "text": "memoir",
        "translation": {
          "text": "РјРµРјСѓР°СЂ",
          "note": null
        }
      },
      {
        "text": "supper",
        "translation": {
          "text": "СѓР¶РёРЅ",
          "note": "СѓСЃС‚Р°СЂРµРІС€РµРµ"
        }
      },
      {
        "text": "meadow",
        "translation": {
          "text": "Р»СѓРі",
          "note": null
        }
      },
      {
        "text": "nail",
        "translation": {
          "text": "РіРІРѕР·РґСЊ",
          "note": null
        }
      },
      {
        "text": "prince",
        "translation": {
          "text": "РїСЂРёРЅС†",
          "note": null
        }
      },
      {
        "text": "subway",
        "translation": {
          "text": "РјРµС‚СЂРѕ",
          "note": null
        }
      },
      {
        "text": "veteran",
        "translation": {
          "text": "РІРµС‚РµСЂР°РЅ",
          "note": null
        }
      },
      {
        "text": "wallet",
        "translation": {
          "text": "Р±СѓРјР°Р¶РЅРёРє",
          "note": null
        }
      },
      {
        "text": "town",
        "translation": {
          "text": "Р°РґРјРёРЅРёСЃС‚СЂР°С†РёСЏ РіРѕСЂРѕРґР°",
          "note": null
        }
      },
      {
        "text": "gravel",
        "translation": {
          "text": "РіСЂР°РІРёР№",
          "note": null
        }
      },
      {
        "text": "calcium",
        "translation": {
          "text": "РєР°Р»СЊС†РёР№",
          "note": null
        }
      },
      {
        "text": "outlet",
        "translation": {
          "text": "РґРёСЃРєРѕРЅС‚ С†РµРЅС‚СЂ",
          "note": ""
        }
      },
      {
        "text": "law",
        "translation": {
          "text": "СЋСЂРёСЃРїСЂСѓРґРµРЅС†РёСЏ",
          "note": null
        }
      },
      {
        "text": "heel",
        "translation": {
          "text": "РїСЏС‚РєР°",
          "note": null
        }
      }
    ]
  }
]
 */
