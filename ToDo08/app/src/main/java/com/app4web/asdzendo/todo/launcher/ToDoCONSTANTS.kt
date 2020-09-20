package com.app4web.asdzendo.todo.launcher

import java.util.*

//Создайте область сопрограммы для использования в вашем приложении чтобы не блокировать onCreate:
var COUNTSFact = 100
//val APPlicationScope = CoroutineScope(Dispatchers.Default)

//var BASE_IN_MEMORY = true
var BASE_IN_MEMORY = false
var FACT_TODO_DATABASE_NAME = "FACT_TODO_ENUM"
var FilterDateStart: Calendar = GregorianCalendar.getInstance().also {
    it.set(2020, 8, 10,0,0,0)}

var FilterDateEnd:   Calendar = GregorianCalendar.getInstance().also {
    it.set(2020, 8, 11,23,59,59)}

    enum class PAEMI(val paemiString:String = " "){
        N("Null"),
        I("Idea"),
        P("Plan"),
        A("Action"),
        E("Event"),
        M("Money"),
        S("System"),
        Z("Rezerv")
    }
