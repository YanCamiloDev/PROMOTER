package net.yan.kotlin.promoters.venda.optionOne

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.yan.kotlin.promoters.R

class OptionOneViewModel(resources: Resources) : ViewModel() {

    val nome = MutableLiveData<String>()
    val nomesLista = MutableLiveData<Array<String>>()

    init {
        nomesLista.value = resources.getStringArray(R.array.clientes)
        nome.value = null
    }

    fun selecionarNome(name: String){
        nome.value = name
    }
    fun selecionarNomeExit(){
        nome.value = null
    }
}