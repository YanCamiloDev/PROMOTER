package net.yan.kotlin.promoters.venda.optionTree

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OptionTreeViewModelFactory: ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OptionTreeViewModel::class.java)) {
            return OptionTreeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}