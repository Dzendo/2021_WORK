package com.app4web.asdzendo.todo.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app4web.asdzendo.todo.database.Fact
import com.app4web.asdzendo.todo.databinding.ToDoRecyclerItemBinding

class ToDoPageAdapter (private val clickListener: FactListener) : PagingDataAdapter<Fact, FactViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder =
        FactViewHolder.from(parent)

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * Этот обратный вызов diff информирует адаптер PagedList о том, как вычислить различия в списках при создании нового
         * * Появляются списки постраничных сообщений.
         * <P>в
         * Когда вы добавляете сыр с помощью кнопки "Добавить", PagedListAdapter использует diffCallback для
         * обнаружьте, что есть только одно отличие элемента от предыдущего, поэтому ему нужно только анимировать и
         * повторная привязка одного вида.
         *
         * @see DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Fact>() {
            override fun areItemsTheSame(oldItem: Fact, newItem: Fact): Boolean =
                oldItem.factId == newItem.factId
            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             * Обратите внимание, что в kotlin == проверка классов данных сравнивает все содержимое, но в Java,
             * обычно вы реализуете Object#equals и используете его для сравнения содержимого объекта.
             */
            override fun areContentsTheSame(oldItem: Fact, newItem: Fact): Boolean =
                oldItem == newItem
        }
    }
}

/**
 * A simple ViewHolder that can bind a Cheese item. It also accepts null items since the data may
 * not have been fetched before it is bound.
 * Простой ViewHolder, который может связать элемент сыра. Он также принимает нулевые элементы, так как данные могут
 * не были принесены до того, как он будет связан.
 */
class FactViewHolder private constructor(private val binding: ToDoRecyclerItemBinding)
    : RecyclerView.ViewHolder(binding.root){

    companion object {
        fun from(parent: ViewGroup): FactViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ToDoRecyclerItemBinding.inflate(layoutInflater, parent, false)
            return FactViewHolder(binding)
        }
    }

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     * Элементы могут быть пустыми, если они еще не выгружены. Адаптер PagedList повторно свяжет
     * ViewHolder при загрузке элемента.
     */
    fun bind(clickListener: FactListener, fact : Fact?) {
        binding.fact = fact
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}
// Вызывается из XML при нажатии на элемент списка RecyclerView через лямбду
class FactListener(val clickListener: (factId: Int) -> Unit) {
    fun onClick(fact: Fact) = clickListener(fact.factId)
}



