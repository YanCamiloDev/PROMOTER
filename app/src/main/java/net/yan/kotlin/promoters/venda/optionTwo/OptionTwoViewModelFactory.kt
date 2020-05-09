package net.yan.kotlin.promoters.venda.optionTwo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OptionTwoViewModelFactory: ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OptionTwoViewModel::class.java)) {
            return OptionTwoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}