package net.yan.kotlin.promoters.venda


import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yan.kotlin.promoters.databinding.CardForPromBinding
import net.yan.kotlin.promoters.model.Promoter
import java.util.*

class AdapterVenda(val click: Clique) :
    ListAdapter<Data, RecyclerView.ViewHolder>(ClienteCallBack()),
    Filterable {

    val uiScope = CoroutineScope(Dispatchers.Default)
    private var lista: List<Data.DataItem>? = null

    var selectionTracker: SelectionTracker<Long>? = null
    fun setSelection(selectionTracker: SelectionTracker<Long>?) {
        this.selectionTracker = selectionTracker
    }

    fun adicionarLista(array: Array<Promoter>?) {
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
                holder.bind(click, nightItem.promotor, position, selectionTracker)
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

    class MyViewHolder private constructor(val binding: CardForPromBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val details: Details = Details()
        var selectionTrackerr: SelectionTracker<Long>? = null

        fun bind(
            click: Clique,
            nome: Promoter,
            position: Int,
            selectionTracker: SelectionTracker<Long>?
        ) {
            binding.promoter = nome
            binding.click = click
            details.position = position.toLong()
            selectionTrackerr = selectionTracker
            if (selectionTrackerr != null) {
                bindSelectedState()
            }
            binding.executePendingBindings()
        }

        private fun bindSelectedState() {
            binding.card.isChecked = selectionTrackerr!!.isSelected(details.selectionKey)
        }

        fun getItemDetails(): ItemDetails<Long?>? {
            return details
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = CardForPromBinding.inflate(inflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }


    internal class Details : ItemDetailsLookup.ItemDetails<Long?>() {
        var position: Long = 0
        override fun getPosition(): Int {
            return position.toInt()
        }

        override fun getSelectionKey(): Long? {
            return position
        }

        override fun inSelectionHotspot(e: MotionEvent): Boolean {
            return true
        }

        override fun inDragRegion(e: MotionEvent): Boolean {
            return false
        }
    }

    class DetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long?>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            var cont = 0
            if (view != null) {
                cont += 1
                if (cont == 1) {
                    val viewHolder = recyclerView.getChildViewHolder(view)
                    if (viewHolder is AdapterVenda.MyViewHolder) {
                        return viewHolder.getItemDetails()
                    }
                }

            }
            return null
        }

    }

    class KeyProvider(adapter: AdapterVenda?) :
        ItemKeyProvider<Long?>(SCOPE_MAPPED) {
        //Pega as chaves únicas de cada card do recyclerView
        override fun getKey(position: Int): Long? {
            return position.toLong()
        }

        //retorna a posição do card
        override fun getPosition(key: Long): Int {
            return key.toInt()
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
                        if (row.promotor.nome.toLowerCase(Locale.ROOT).contains(const)) {
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
        return oldItem.promotor == newItem.promotor
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }

}

class Clique(val clickListener: (sleepId: Promoter) -> Unit) {
    fun onClick(nome: Promoter) = clickListener(nome)
}

sealed class Data {
    data class DataItem(val name: Promoter) : Data() {
        override val promotor = name
    }

    abstract val promotor: Promoter
}
