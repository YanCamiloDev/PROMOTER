package net.yan.kotlin.promoters.principal


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.databinding.FragmentHomeBinding

class HomeViewModel(
    val dataSource: FirebaseHelper,
    val binding: FragmentHomeBinding
) : ViewModel() {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Default + job)
    val estaLogado = MutableLiveData<Boolean>()
    val newPhoto = MutableLiveData<Boolean>()

    init {
        val user = dataSource.auth?.currentUser
        estaLogado.value = user != null
    }

    fun sair() {
        FirebaseAuth.getInstance().signOut()
        estaLogado.value = false
    }

    fun addLocalAndVenda(){
        newPhoto.value = true
    }
    fun addLocalAndVendaClose(){
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