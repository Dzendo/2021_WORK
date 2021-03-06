package com.app4web.asdzendo.todo.ui.todo

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.databinding.ToDoHeaderListItemBinding
import com.app4web.asdzendo.todo.databinding.ToDoRecyclerCardItemBinding
import com.app4web.asdzendo.todo.databinding.ToDoRecyclerListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * [RecyclerView.Adapter] that can display a [DataItem].
 */
private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1
private const val ITEM_VIEW_TYPE_CARD = 2

class ToDoAdapter(private val clickListener: FactListener):
        ListAdapter<DataItem, RecyclerView.ViewHolder>(FactDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    // Для реакции на нажатие строки или карточки - для заголовка пока не сделана сортировка
    override fun onBindViewHolder(holder: RecyclerView. ViewHolder, position: Int) {
        when (holder) {
            is ListViewHolder -> {
                val factItem = getItem(position) as DataItem.FactItem
                holder.bind(clickListener, factItem.fact)
            }
            is CardViewHolder -> {
                val factItem = getItem(position) as DataItem.FactCard
                holder.bind(clickListener, factItem.fact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ListViewHolder.from(parent)
            ITEM_VIEW_TYPE_CARD -> CardViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    // Вместо того, чтобы использовать submitList(), предоставленный ListAdapter,
    // для отправки вашего списка, вы будете использовать эту функцию,
    // чтобы добавить заголовок, а затем отправить список.
    fun addHeaderAndSubmitList(list: List<Fact>?) {
        // запустите сопрограмму в, adapterScope чтобы управлять списком
        adapterScope.launch {
            //если переданный список есть null, верните только заголовок,
            // в противном случае присоедините заголовок к заголовку списка,
            // а затем отправьте список.
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.FactItem(it) }
            }
            // Затем переключитесь в Dispatchers.Main контекст, чтобы отправить список
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
    // Вместо того, чтобы использовать submitList(), предоставленный ListAdapter,
    // для отправки вашего списка, вы будете использовать эту функцию,
    fun addSubmitCard(list: List<Fact>?) {
        // запустите сопрограмму в, adapterScope чтобы управлять списком
        adapterScope.launch {
            //Список в карточки
            val items = list?.map { DataItem.FactCard(it) }

            // Затем переключитесь в Dispatchers.Main контекст, чтобы отправить список
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    // 23,2 Добавьте класс TextHolder внутри SleepNightAdapter класса.
    class TextViewHolder private constructor(binding: ToDoHeaderListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ToDoHeaderListItemBinding.inflate(layoutInflater, parent, false)
                return TextViewHolder(binding)
            }
        }
    }
   // override fun getItemCount(): Int = values.size
   // перечень фактов в строках для Landscape
   class ListViewHolder private constructor(private val binding:ToDoRecyclerListItemBinding)
       : RecyclerView.ViewHolder(binding.root) {

       fun bind(clickListener: FactListener, item: Fact) {
           binding.fact = item
           binding.clickListener = clickListener
           binding.executePendingBindings()  // попоросить привязку выполнить обновление сразу
       }
       companion object {
           fun from(parent: ViewGroup): ListViewHolder {
               val layoutInflater = LayoutInflater.from(parent.context)
               val binding = ToDoRecyclerListItemBinding.inflate(layoutInflater, parent, false)
               return ListViewHolder(binding)
           }
       }
   }
    // перечень фактов в карточках для Portrait
    class CardViewHolder private constructor(private val binding:ToDoRecyclerCardItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: FactListener, item: Fact) {
            binding.fact = item
            binding.clickListener = clickListener
            binding.executePendingBindings()  // попоросить привязку выполнить обновление сразу
        }
        companion object {
            fun from(parent: ViewGroup): CardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ToDoRecyclerCardItemBinding.inflate(layoutInflater, parent, false)
                return CardViewHolder(binding)
            }
        }
    }

    //23,6 Затем, чтобы выяснить, какой тип представления вернуть, добавьте проверку,
// чтобы увидеть, какой тип элемента находится в списке:
    // Затем переопределите getItemViewType и верните правильный тип представления элемента.
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.FactItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.FactCard -> ITEM_VIEW_TYPE_CARD
        }
    }
}
// Умное обновление изменившихся элементов на экране, а не всех
//23,5 Обновите ваш DiffCallback для обработки DataItem вместо SleepNight:
class FactDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem == newItem
}
// Вызывается из XML при нажатии на элемент списка RecyclerView через лямбду
class FactListener(val clickListener: (factId: Long) -> Unit) {
    fun onClick(fact: Fact) = clickListener(fact.factId)
}
// 23.1. Внизу SleepNightAdapter добавьте запечатанный класс с именем DataItem:
sealed class DataItem {
    abstract val id: Long

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }
    data class FactItem(val fact: Fact): DataItem() {
        override val id = fact.factId
    }
    data class FactCard(val fact: Fact): DataItem() {
        override val id = fact.factId
    }
}