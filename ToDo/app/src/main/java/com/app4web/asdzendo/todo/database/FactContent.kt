package com.app4web.asdzendo.todo.database

import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * Вспомогательный класс для предоставления примерного контента для пользовательских интерфейсов,
 * созданных Мастера шаблонов Android.
 *
 */
object FactContent {

    private const val COUNT = 650L

    private val PAEMI: List<String> = arrayListOf(" ","P","A","E","M","I","S")
    /**
     * An array and map of sample (dummy) items , by ID..
     */
    val FACTS: MutableList<Fact> = ArrayList()
    //private val PAEMI_MAP: MutableMap<Long, Fact> = HashMap()

    init {
        // Add some sample items.
        for (id in 1L..COUNT)
            for (paemi in PAEMI)
                addFactItem(createFactItem(id,paemi))
        //     FACT_MAP[fact.factId] = fact
    }
   
    private fun addFactItem(fact: Fact) = FACTS.add(fact)

    private fun createFactItem(id: Long= 0L, paemi: String = "S"): Fact =
        Fact(paemi = paemi, nameShort= "$id Факт", name= makeDetails(id))

    private fun makeDetails(id: Long): String = "Факт полностью: $id"
}