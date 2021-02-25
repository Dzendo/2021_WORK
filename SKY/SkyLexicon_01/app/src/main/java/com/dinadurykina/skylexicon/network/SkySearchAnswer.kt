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
data class SkySearchAnswer(
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
    "id": 838,
    "text": "chair",
    "meanings": [
      {
        "id": 1938,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "СЃС‚СѓР»",
          "note": null
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/b905a618b56c721ce683164259ac02c4.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/b905a618b56c721ce683164259ac02c4.jpeg?w=640&h=480",
        "transcription": "К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=chair"
      },
      {
        "id": 1940,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РїСЂРµРґСЃРµРґР°С‚РµР»СЊ",
          "note": null
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/55dbfb9c96ddf4f21affd3c4dd05706a.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/55dbfb9c96ddf4f21affd3c4dd05706a.jpeg?w=640&h=480",
        "transcription": "К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=chair"
      },
      {
        "id": 1939,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РґРѕР»Р¶РЅРѕСЃС‚СЊ РїСЂРѕС„РµСЃСЃРѕСЂР°",
          "note": ""
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/577c8a22cd8140ec9d3d2bf97c72a542.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/577c8a22cd8140ec9d3d2bf97c72a542.jpeg?w=640&h=480",
        "transcription": "К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=chair"
      },
      {
        "id": 1941,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "СЌР»РµРєС‚СЂРёС‡РµСЃРєРёР№ СЃС‚СѓР»",
          "note": ""
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/5ce160bf580717f540be3e725753d802.png?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/5ce160bf580717f540be3e725753d802.png?w=640&h=480",
        "transcription": "К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=chair"
      },
      {
        "id": 1942,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РјРµСЃС‚Рѕ",
          "note": "РІ РѕСЂРєРµСЃС‚СЂРµ"
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/cab7367d8fa87b5c42739bc74e3d1a4d.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/cab7367d8fa87b5c42739bc74e3d1a4d.jpeg?w=640&h=480",
        "transcription": "К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=chair"
      },
      {
        "id": 1943,
        "partOfSpeechCode": "v",
        "translation": {
          "text": "СЂСѓРєРѕРІРѕРґРёС‚СЊ",
          "note": "С‡РµРј-Р»РёР±Рѕ"
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/28f5140b6e865ca003db8c98d96b45f6.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/28f5140b6e865ca003db8c98d96b45f6.jpeg?w=640&h=480",
        "transcription": "К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=chair"
      }
    ]
  },
  {
    "id": 71792,
    "text": "bath chair",
    "meanings": [
      {
        "id": 38646,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РєСЂРµСЃР»Рѕ РЅР° РєРѕР»С‘СЃР°С… РґР»СЏ Р±РѕР»СЊРЅС‹С…",
          "note": ""
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/dc5f01f495ea5afdfe788a1dda797580.png?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/dc5f01f495ea5afdfe788a1dda797580.png?w=640&h=480",
        "transcription": "bЙ‘ЛђОё К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=bath+chair"
      }
    ]
  },
  {
    "id": 98100,
    "text": "barber chair",
    "meanings": [
      {
        "id": 72065,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РїР°СЂРёРєРјР°С…РµСЂСЃРєРѕРµ РєСЂРµСЃР»Рѕ",
          "note": ""
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/e7d4cd26af86774c367798598bd8bd3e.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/e7d4cd26af86774c367798598bd8bd3e.jpeg?w=640&h=480",
        "transcription": "Л€bЙ‘ЛђbЙ™ К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=barber+chair"
      }
    ]
  },
  {
    "id": 98616,
    "text": "lounge chair",
    "meanings": [
      {
        "id": 217290,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РјСЏРіРєРѕРµ РєСЂРµСЃР»Рѕ",
          "note": ""
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/4c7906373a22557e2e7403a9b07dff2a.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/4c7906373a22557e2e7403a9b07dff2a.jpeg?w=640&h=480",
        "transcription": "laКЉnК¤ К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=lounge+chair"
      }
    ]
  },
  {
    "id": 158880,
    "text": "high chair",
    "meanings": [
      {
        "id": 234337,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РІС‹СЃРѕРєРёР№ РґРµС‚СЃРєРёР№ СЃС‚СѓР»СЊС‡РёРє",
          "note": null
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/c4cad32685cbb9b56aab45a399f52542.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/c4cad32685cbb9b56aab45a399f52542.jpeg?w=640&h=480",
        "transcription": "haЙЄ К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=high+chair"
      }
    ]
  },
  {
    "id": 165746,
    "text": "chair days",
    "meanings": [
      {
        "id": 234598,
        "partOfSpeechCode": "ph",
        "translation": {
          "text": "СЃС‚Р°СЂРѕСЃС‚СЊ",
          "note": null
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/5ad30fb117477b3bc3968d7bfeab998d.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/5ad30fb117477b3bc3968d7bfeab998d.jpeg?w=640&h=480",
        "transcription": "К§eЙ™ deЙЄz",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=chair+days"
      }
    ]
  },
  {
    "id": 78955,
    "text": "camp chair",
    "meanings": [
      {
        "id": 133169,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "СЃРєР»Р°РґРЅРѕР№ СЃС‚СѓР»",
          "note": null
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/5c85d1c83fab0266d19f099dcc4658f9.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/5c85d1c83fab0266d19f099dcc4658f9.jpeg?w=640&h=480",
        "transcription": "kГ¦mp К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=camp+chair"
      }
    ]
  },
  {
    "id": 112673,
    "text": "musical chair",
    "meanings": [
      {
        "id": 36305,
        "partOfSpeechCode": "ph",
        "translation": {
          "text": "РјСѓР·С‹РєР°Р»СЊРЅС‹Рµ СЃС‚СѓР»СЊСЏ",
          "note": null
        },
        "previewUrl": "",
        "imageUrl": "",
        "transcription": "Л€mjuЛђzЙЄkЙ™l К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=musical+chair"
      }
    ]
  },
  {
    "id": 81703,
    "text": "sedan chair",
    "meanings": [
      {
        "id": 38126,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РїРѕСЂС‚С€РµР·",
          "note": "РёСЃС‚РѕСЂ."
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/b1a0c60c3590d46d3e39cc1897cd9e30.png?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/b1a0c60c3590d46d3e39cc1897cd9e30.png?w=640&h=480",
        "transcription": "sЙЄЛ€dГ¦n К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=sedan+chair"
      }
    ]
  },
  {
    "id": 116595,
    "text": "reclining chair",
    "meanings": [
      {
        "id": 85075,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РѕС‚РєРёРґС‹РІР°СЋС‰РµРµСЃСЏ РєСЂРµСЃР»Рѕ",
          "note": null
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/cf1442639c37f79f5a2cad9947c134b3.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/cf1442639c37f79f5a2cad9947c134b3.jpeg?w=640&h=480",
        "transcription": "rЙЄЛ€klaЙЄnЙЄЕ‹ К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=reclining+chair"
      }
    ]
  },
  {
    "id": 85527,
    "text": "death chair",
    "meanings": [
      {
        "id": 126364,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "СЌР»РµРєС‚СЂРёС‡РµСЃРєРёР№ СЃС‚СѓР»",
          "note": ""
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/41d62ee8d269c7cae5abe279d7c5c563.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/41d62ee8d269c7cae5abe279d7c5c563.jpeg?w=640&h=480",
        "transcription": "dЙ›Оё К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=death+chair"
      }
    ]
  },
  {
    "id": 92374,
    "text": "morris chair",
    "meanings": [
      {
        "id": 63363,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РјРѕСЂСЂРёСЃ РєСЂРµСЃР»Р°",
          "note": null
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/11c9027ca2fb4976ee339e9434885eef.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/11c9027ca2fb4976ee339e9434885eef.jpeg?w=640&h=480",
        "transcription": "Л€mЙ’rЙЄs К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=morris+chair"
      }
    ]
  },
  {
    "id": 97506,
    "text": "swivel chair",
    "meanings": [
      {
        "id": 202284,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РІСЂР°С‰Р°СЋС‰РёР№СЃСЏ СЃС‚СѓР»",
          "note": ""
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/16123555dbd4317fe339b1881f01dbc0.png?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/16123555dbd4317fe339b1881f01dbc0.png?w=640&h=480",
        "transcription": "Л€swЙЄvl К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=swivel+chair"
      }
    ]
  },
  {
    "id": 78937,
    "text": "easy chair",
    "meanings": [
      {
        "id": 201397,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РєСЂРµСЃР»Рѕ",
          "note": ""
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/4c7906373a22557e2e7403a9b07dff2a.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/4c7906373a22557e2e7403a9b07dff2a.jpeg?w=640&h=480",
        "transcription": "Л€iЛђzi К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=easy+chair"
      }
    ]
  },
  {
    "id": 99414,
    "text": "rocking chair",
    "meanings": [
      {
        "id": 198900,
        "partOfSpeechCode": "n",
        "translation": {
          "text": "РєСЂРµСЃР»Рѕ-РєР°С‡Р°Р»РєР°",
          "note": null
        },
        "previewUrl": "//d2zkmv5t5kao9.cloudfront.net/images/54460c3b707977a0b599438c97a23bd4.jpeg?w=96&h=72",
        "imageUrl": "//d2zkmv5t5kao9.cloudfront.net/images/54460c3b707977a0b599438c97a23bd4.jpeg?w=640&h=480",
        "transcription": "Л€rЙ’kЙЄЕ‹ К§eЙ™",
        "soundUrl": "//d2fmfepycn0xw0.cloudfront.net?gender=male&accent=british&text=rocking+chair"
      }
    ]
  }
]
 */
