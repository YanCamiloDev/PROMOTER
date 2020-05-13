package net.yan.kotlin.promoters.venda.optionTwo

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OptionTwoViewModelFactory(val resources: Resources): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OptionTwoViewModel::class.java)) {
            return OptionTwoViewModel(resources) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}