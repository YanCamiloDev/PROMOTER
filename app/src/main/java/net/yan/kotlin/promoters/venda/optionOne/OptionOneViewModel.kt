package net.yan.kotlin.promoters.venda.optionOne

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.model.Cliente

class OptionOneViewModel(resources: Resources) : ViewModel() {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Default + job)
    val nome = MutableLiveData<Cliente>()
    val nomesLista = MutableLiveData<Array<Cliente>>()
    val lista = mutableListOf<Cliente>()

    init {
        if (nomesLista.value == null) {
            uiScope.launch {
                try {
                    val firebase = FirebaseHelper()
                    val ref = firebase.database!!.child("Clientes")
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (data in p0.children) {
                                val cliente: Cliente = data.getValue(
                                    Cliente::class.java
                                )!!
                                cliente.id = data.key.toString()
                                lista.add(cliente)
                            }
                            nomesLista.value = lista.toTypedArray()
                        }
                    })
                } catch (e: FirebaseException) {
                    Log.i("ERRO", "CLIENTES DATABASE")
                }
            }
        }
    }

    fun selecionarNome(name: Cliente){
        nome.value = name
    }
    fun selecionarNomeExit(){
        nome.value = null
    }
}