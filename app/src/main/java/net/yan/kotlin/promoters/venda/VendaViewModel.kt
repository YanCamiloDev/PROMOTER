package net.yan.kotlin.promoters.venda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class VendaViewModel: ViewModel() {

    val job = Job()
    val coroutine = CoroutineScope(Dispatchers.IO + job)

    val foto = MutableLiveData<Boolean>()

    fun tirarFoto(){
        foto.value = true
    }


    fun tirarFotoClose(){
        foto.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}