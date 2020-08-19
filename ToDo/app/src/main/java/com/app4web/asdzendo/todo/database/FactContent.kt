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

    /**
     * An array of sample (dummy) items.
     */
  
    val FACTS: MutableList<Fact> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */

    private const val COUNT = 65L
    //private val PAEMI_MAP: MutableMap<Long, Fact> = HashMap()
    private val PAEMI: List<String> = arrayListOf(" ","P","A","E","M","I","S")


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