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
   
   // private val FACT_MAP: MutableMap<Long, Fact> = HashMap()
    private val PAEMI: List<String> = arrayListOf("I","P","A","E","M")
    private const val COUNT = 65L

    init {
        // Add some sample items.
        for (i in 1L..COUNT)
            for (paemi in PAEMI)
                addFactItem(createFactItem(i,paemi))
    }
   
    private fun addFactItem(fact: Fact) {
        FACTS.add(fact)
   //     FACT_MAP[fact.factId] = fact
    }
    
    private fun createFactItem(id: Long= 0L, paemi: String = "S"): Fact {
        return Fact(paemi = paemi, nameShort= "$id Факт", name= makeDetails(id))
    }

    private fun makeDetails(position: Long): String {
        val builder = StringBuilder()
        builder.append("Факт полностью: ").append(position)
       
        return builder.toString()
    }
    
}