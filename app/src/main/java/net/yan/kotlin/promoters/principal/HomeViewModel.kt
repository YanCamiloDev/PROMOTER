package net.yan.kotlin.promoters.principal


import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import net.yan.kotlin.promoters.R
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.databinding.FragmentHomeBinding
import net.yan.kotlin.promoters.model.Cidade

class HomeViewModel(
    val dataSource: FirebaseHelper,
    val binding: FragmentHomeBinding,
    val resources: Resources
) : ViewModel() {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.IO + job)
    val estaLogado = MutableLiveData<Boolean>()
    val newPhoto = MutableLiveData<Boolean>()

    init {
        uiScope.launch {
            val user = dataSource.auth?.currentUser
            withContext(Dispatchers.Main) {
                estaLogado.value = user != null
            }
        }

    }

    fun sair() {
        uiScope.launch {
            FirebaseAuth.getInstance().signOut()
            withContext(Dispatchers.Main){
                estaLogado.value = false
            }
        }
    }

    fun addLocalAndVenda() {
        newPhoto.value = true
    }

    fun addLocalAndVendaClose() {
        newPhoto.value = false
    }

    fun novaTela() {
        estaLogado.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}