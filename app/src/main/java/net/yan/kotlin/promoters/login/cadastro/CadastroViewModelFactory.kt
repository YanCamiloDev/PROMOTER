package net.yan.kotlin.promoters.login.cadastro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.databinding.FragmentCadastroBinding
import net.yan.kotlin.promoters.login.LoginViewModel

class CadastroViewModelFactory(
private val dataSource: FirebaseHelper,
private val binding: FragmentCadastroBinding
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastroViewModel::class.java)) {
            return CadastroViewModel(dataSource, binding) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}