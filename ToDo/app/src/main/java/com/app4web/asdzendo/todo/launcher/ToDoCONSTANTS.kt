package com.app4web.asdzendo.todo.launcher

import com.app4web.asdzendo.todo.database.FactDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

//Создайте область сопрограммы для использования в вашем приложении чтобы не блокировать onCreate:
const val COUNTSFact = 65L
val APPlicationScope = CoroutineScope(Dispatchers.Default)
lateinit var  FactdataSource: FactDatabaseDao
val PAEMI: List<String> = arrayListOf(" ","P","A","E","M","I","S")