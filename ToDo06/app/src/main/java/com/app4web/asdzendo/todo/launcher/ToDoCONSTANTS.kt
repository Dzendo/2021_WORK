package com.app4web.asdzendo.todo.launcher

import java.util.*

const val FACT_TODO_DATABASE_NAME = "FACT_TODO"
//Создайте область сопрограммы для использования в вашем приложении чтобы не блокировать onCreate:
var COUNTSFact = 650L
//val APPlicationScope = CoroutineScope(Dispatchers.Default)
//lateinit var  FactdataSource: FactDatabaseDao
val PAEMI: List<String> = arrayListOf(" ","P","A","E","M","I","S")
//val PAEMI: List<Char> = arrayListOf(' ','P','A','E','M','I','S')
//lateinit var  factRepository: FactRepository
var BASE_IN_MEMORY = false
//var BASE_IN_MEMORY = false
var FilterDateStart:Date = Date(120,0, 1, 1, 0, 0)
var FilterDateEnd: Date = Date(120, 11, 31, 0, 0)
