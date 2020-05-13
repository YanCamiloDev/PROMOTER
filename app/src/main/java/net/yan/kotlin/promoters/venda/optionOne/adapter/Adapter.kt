package net.yan.kotlin.promoters.venda.optionOne.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yan.kotlin.promoters.databinding.CardOneBinding
import net.yan.kotlin.promoters.databinding.FragmentOptionOneBinding
import net.yan.kotlin.promoters.databinding.FragmentVendaBinding
import net.yan.kotlin.promoters.model.Cliente
import java.util.*


private val ITEM_VIEW_TYPE_ITEM = 1

class Adapter(val clickListener: ClienteListener) : ListAdapter<DataItem,
        RecyclerView.ViewHolder>(ClientCallBack()), Filterable {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    private var itens: List<DataItem.ClienteItem>? = null



    fun addHeaderAndSubmitList(list: Array<Cliente
            >?) {
        adapterScope.launch {
            itens = list?.map { DataItem.ClienteItem(it) }

            withContext(Dispatchers.Main) {
                submitList(itens)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.ClienteItem
                holder.bind(clickListener, nightItem.cliente)
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

    class ViewHolder private constructor(val binding: CardOneBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ClienteListener, nome: Cliente) {
            binding.click = clickListener
            binding.cliente = nome
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardOneBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().toLowerCase(Locale.ROOT).trim()
                var lista = mutableListOf<DataItem.ClienteItem>()
                if (charSearch.isEmpty()) {
                    lista.addAll(itens!!)
                } else {
                    for (row in itens!!) {
                        if (row.cliente.endereco.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            lista.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = lista
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as MutableList<DataItem>?)
            }

        }
    }
}


class ClientCallBack : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.cliente == newItem.cliente
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

class ClienteListener(val clickListener: (sleepId: Cliente) -> Unit) {
    fun onClick(client: Cliente) = clickListener(client)
}

sealed class DataItem {
    data class ClienteItem(val cli: Cliente) : DataItem() {
        override val cliente = cli
    }

    abstract val cliente: Cliente
}