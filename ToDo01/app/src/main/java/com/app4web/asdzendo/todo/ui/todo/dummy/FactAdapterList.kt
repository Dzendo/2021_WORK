package com.app4web.asdzendo.todo.ui.todo.dummy

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.database.Fact

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class FactAdapterList(
    private val values: List<Fact>
) : RecyclerView.Adapter<FactAdapterList.ViewHolder>() {

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