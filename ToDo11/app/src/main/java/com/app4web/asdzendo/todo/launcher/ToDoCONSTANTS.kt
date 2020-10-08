package com.app4web.asdzendo.todo.launcher

import java.util.*
// Файл для описания констант и временных переменных вместо setting
enum class PAEMI(val paemiString:String = " "){
    N("Null"),
    I("Idea"),
    P("Plan"),
    A("Action"),
    E("Event"),
    M("Money"),
    S("System"),
    R("Rezerv")
}

var FACT_TODO_DATABASE_NAME = "FACT_TODO_ENUM"
var COUNTSFact = 100   // пачка для дозаполнения базы по умолчанию

//Создайте область сопрограммы для использования в вашем приложении чтобы не блокировать onCreate:
//val APPlicationScope = CoroutineScope(Dispatchers.Default)


var BASE_IN_MEMORY = false  // база на HDD
var FilterDateStart: Calendar = GregorianCalendar.getInstance().also {
    it.set(2020, 8, 10,0,0,0)}
var FilterDateEnd:   Calendar = GregorianCalendar.getInstance().also {
    it.set(2020, 8, 11,23,59,59)}
/*
var BASE_IN_MEMORY = true   // база в RAM
var FilterDateStart: Calendar = GregorianCalendar.getInstance().also {
    it.set(1900, 0, 0,0,0,0)}
var FilterDateEnd:   Calendar = GregorianCalendar.getInstance().also {
    it.set(2100, 11, 31,23,59,59)}
*/

