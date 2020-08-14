package com.app4web.asdzendo.todo.ui.todo

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.ui.todo.dummy.DummyContent

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ToDoAdapterList(
    private val values: List<Fact>
) : RecyclerView.Adapter<ToDoAdapterList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dummy_recycler_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.factId.toString()
        holder.contentView.text = item.nameShort
        holder.detailsView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val detailsView: TextView = view.findViewById(R.id.details)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
// Умное обновление изменившихся элементов на экране, а не всех
//23,5 Обновите ваш DiffCallback для обработки DataItem вместо SleepNight:
class FactDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}
// Вызывается из XML при нажатии на элемент списка RecyclerView через лямбду
class FactListener(val clickListener: (factId: Long) -> Unit) {
    fun onClick(fact: Fact) = clickListener(fact.factId)
}
// 23.1. Внизу SleepNightAdapter добавьте запечатанный класс с именем DataItem:
sealed class DataItem {
    data class FactItem(val fact: Fact): DataItem() {
        override val id = fact.factId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}