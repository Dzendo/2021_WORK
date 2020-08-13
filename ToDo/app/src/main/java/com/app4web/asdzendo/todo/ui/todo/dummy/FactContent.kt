package com.app4web.asdzendo.todo.ui.todo.dummy

import com.app4web.asdzendo.todo.database.Fact
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * Вспомогательный класс для предоставления примерного контента для пользовательских интерфейсов, созданных
 * Мастера шаблонов Android.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object FactContent {

    /**
     * An array of sample (dummy) items.
     */
  
    val FACTS: MutableList<Fact> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
   
    private val FACT_MAP: MutableMap<Long, Fact> = HashMap()

    private const val COUNT = 45

    init {
        // Add some sample items.
        for (i in 1..COUNT) addFactItem(createFactItem(i.toLong()))
    }
   
    private fun addFactItem(fact: Fact) {
        FACTS.add(fact)
        FACT_MAP[fact.factId] = fact
    }
    
    private fun createFactItem(id: Long): Fact {
        return Fact(id, nameShort= "Факт $id", name= makeDetails(id.toInt()))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Факт полностью: ").append(position)
       
        return builder.toString()
    }
    
}