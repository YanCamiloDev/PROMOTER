package net.yan.kotlin.promoters.login.cadastro.continuacao

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.databinding.FragmentContinuacaoBinding


class ContinuacaoViewModelFactory(
    private val dataSource: FirebaseHelper,
    private val binding: FragmentContinuacaoBinding
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContinuacaoViewModel::class.java)) {
            return ContinuacaoViewModel(dataSource, binding) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}