package net.yan.kotlin.promoters.venda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VendaViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VendaViewModel::class.java)) {
            return VendaViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

