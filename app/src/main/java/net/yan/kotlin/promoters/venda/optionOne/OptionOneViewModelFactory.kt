package net.yan.kotlin.promoters.venda.optionOne

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OptionOneViewModelFactory(val resources: Resources): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OptionOneViewModel::class.java)) {
            return OptionOneViewModel(resources) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
