package net.yan.kotlin.promoters.venda.optionTwo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yan.kotlin.promoters.databinding.CardForTwoScreenBinding
import net.yan.kotlin.promoters.model.Cidade
import java.util.*

class AdapterTwo(val click: Clique) : ListAdapter<Data, RecyclerView.ViewHolder>(ClienteCallBack()),
    Filterable {

    val uiScope = CoroutineScope(Dispatchers.Default)
    private var lista: List<Data.DataItem>? = null


    fun adicionarLista(array: Array<Cidade>?) {
        uiScope.launch {
            lista = array?.map { Data.DataItem(it) }
            withContext(Dispatchers.Main) {
                submitList(lista)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder -> {
                val nightItem = getItem(position) as Data.DataItem
                holder.bind(click, nightItem.cidade)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> MyViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Data.DataItem -> 1
            else -> null
        }!!
    }

    class MyViewHolder private constructor(val binding: CardForTwoScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(click: Clique, cidade: Cidade) {
            binding.cidade = cidade
            binding.click = click
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CardForTwoScreenBinding.inflate(inflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val const = constraint.toString().toLowerCase(Locale.ROOT).trim()
                val fLista = mutableListOf<Data.DataItem>()
                if (const.isEmpty()) {
                    fLista.addAll(lista!!)
                } else {
                    for (row in lista!!) {
                        if (row.cidade.local.toLowerCase(Locale.ROOT).contains(const)) {
                            fLista.add(row)
                        }
                    }
                }
                val filter = FilterResults()
                filter.values = fLista
                return filter
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as MutableList<Data>?)
            }

        }
    }

}

class ClienteCallBack : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.cidade == newItem.cidade
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }

}

class Clique(val clickListener: (cida: Cidade) -> Unit) {
    fun onClick(cidade: Cidade) = clickListener(cidade)
}

sealed class Data {
    data class DataItem(val name: Cidade) : Data() {
        override val cidade = name
    }

    abstract val cidade: Cidade
}
