package com.dinadurykina.skylexicon.launcher

// https://classroom.udacity.com/courses/ud9012/lessons/2be0ed85-721d-4a8d-a484-909b5c98336c/concepts/b36af08e-1bce-48e8-9a31-2e268907d2f0
// https://developer.android.com/codelabs/kotlin-android-training-internet-data?index=..%2F..android-kotlin-fundamentals#3
//  https://dictionary.skyeng.ru/doc/api/external

// Файл для описания констант и временных переменных вместо setting
// val skyRepository = SkyRepository()
// String representation of a part of speech
// Строковое представление части речи
enum class PartOfSpeechCode(val partOfSpeechCodeEng: String = " ",partOfSpeechCodeRus: String = " "){
    n ("noun","существительное"),
    v ("verb","глагол"),
    j ("adjective","прилагательное"),
    r ("adverb","наречие"),
    prp ("preposition","предлог"),
    prn ("pronoun","местоимение"),
    crd ("cardinal number","кардинальное число"),
    cjc ("conjunction","связи"),
    exc ("interjection","междометие"),
    det ("article","статьи"),
    abb ("abbreviation","аббревиатура"),
    x ("particle","частица"),
    ord ("ordinal number","порядковый номер"),
    md ("modal verb","модальный глагол"),
    ph ("phrase","фразы"),
    phi ("idiom","идиома")
}

// Создайте область сопрограммы для использования в вашем приложении чтобы не блокировать экраны
// идея создания такой области сопрограмм что она будет работать до остановки app не обращая внимания на экраны
// пока не используется ( кстати можно создать подобную в application)
//val APPlicationScope = CoroutineScope(Dispatchers.Default)

