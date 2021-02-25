package com.dinadurykina.skylexicon.launcher

// Файл для описания констант и временных переменных вместо setting

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

var SKY_DATABASE_NAME = "SKY_LEXICON"
//var COUNTSky = 100   // пачка для дозаполнения базы по умолчанию

// Создайте область сопрограммы для использования в вашем приложении чтобы не блокировать экраны
// идея создания такой области сопрограмм что она будет работать до остановки app не обращая внимания на экраны
// пока не используется ( кстати можно создать подобную в application)
//val APPlicationScope = CoroutineScope(Dispatchers.Default)

// отладочные settings:
// var BASE_IN_MEMORY = false  // база на HDD
// var BASE_IN_MEMORY = true   // база в RAM


