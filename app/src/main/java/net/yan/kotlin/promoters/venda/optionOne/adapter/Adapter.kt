package net.yan.kotlin.promoters.venda.optionOne.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yan.kotlin.promoters.databinding.CardBinding
import net.yan.kotlin.promoters.databinding.FragmentOptionOneBinding

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class Adapter(val clickListener: ClienteListener) : ListAdapter<DataItem,
        RecyclerView.ViewHolder>(ClientCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: Array<String>?) {
        adapterScope.launch {
            val items = list?.map { DataItem.ClienteItem(it) }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.ClienteItem
                holder.bind(clickListener, nightItem.nome)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.ClienteItem -> ITEM_VIEW_TYPE_ITEM
            else -> null
        }!!
    }

    class ViewHolder private constructor(val binding: CardBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ClienteListener, nome: String) {
            binding.click = clickListener
            binding.nome = nome
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class ClientCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class ClienteListener(val clickListener: (sleepId: String) -> Unit) {
    fun onClick(nome: String) = clickListener(nome)
}

sealed class DataItem {
    data class ClienteItem(val nome: String): DataItem() {
        override val id = nome
    }

    abstract val id: String
}