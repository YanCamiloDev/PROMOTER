package net.yan.kotlin.promoters.principal

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.databinding.FragmentHomeBinding

class HomeViewModelFactory(
    private val dataSource: FirebaseHelper,
    private val binding: FragmentHomeBinding,
    private val resources: Resources
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataSource, binding, resources) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
