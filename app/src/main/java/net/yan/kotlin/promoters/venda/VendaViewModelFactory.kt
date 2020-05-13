package net.yan.kotlin.promoters.venda

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.yan.kotlin.promoters.data.FirebaseHelper

class VendaViewModelFactory(private val resources: Resources, private val dataSource: FirebaseHelper): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VendaViewModel::class.java)) {
            return VendaViewModel(resources, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

