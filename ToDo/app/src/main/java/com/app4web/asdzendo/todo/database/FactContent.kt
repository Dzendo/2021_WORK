package com.app4web.asdzendo.todo.database

import com.app4web.asdzendo.todo.launcher.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * Вспомогательный класс для предоставления примерного контента для пользовательских интерфейсов,
 * созданных Мастера шаблонов Android.
 *
 */
fun factContent(countFacts: Long = 65L): MutableList<Fact>  {

    //val PAEMI: List<String> = arrayListOf(" ","P","A","E","M","I","S")
    val FACTS: MutableList<Fact> = ArrayList()
    //private val PAEMI_MAP: MutableMap<Long, Fact> = HashMap()
    /**
     * An array and map of sample (dummy) items , by ID..
     */
    // Add some sample items.
    for (id in 1L..countFacts)
        for (paemi in PAEMI)
            FACTS.add((Fact(paemi = paemi, nameShort= "$id Факт", name= "Факт полностью: $id")))
    //     FACT_MAP[fact.factId] = fact
    return FACTS
}

fun createFactDatabase(countFacts: Long = 65L) = APPlicationScope.launch {
    // Заполнение начальных данных
    //FactdataSource.clear()
    FactdataSource.insertAll(factContent(countFacts))
    Timber.i("ToDoApplication Add Database Строк записи = $countFacts * 7 = ${countFacts * 7}")
}




