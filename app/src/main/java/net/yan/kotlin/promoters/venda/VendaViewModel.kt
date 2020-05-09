package net.yan.kotlin.promoters.venda

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.yan.kotlin.promoters.R

class VendaViewModel(val resouces: Resources): ViewModel() {

    val job = Job()
    val coroutine = CoroutineScope(Dispatchers.IO + job)

    val lista = MutableLiveData<Array<String>>()
    val foto = MutableLiveData<Boolean>()

    init {
        val listaa: Array<String> = resouces.getStringArray(R.array.promotores)
        lista.value = listaa
    }
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