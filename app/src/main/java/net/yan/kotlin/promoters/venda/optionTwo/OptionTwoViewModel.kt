package net.yan.kotlin.promoters.venda.optionTwo

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.yan.kotlin.promoters.R
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.model.Cidade
import net.yan.kotlin.promoters.model.Cliente

class OptionTwoViewModel(resources: Resources): ViewModel() {

    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.IO + job)
    val cidade = MutableLiveData<Cidade>()
    val nomesLista = MutableLiveData<Array<Cidade>>()
    val lista = mutableListOf<Cidade>()


    init {
        if (nomesLista.value == null) {
            uiScope.launch {
                try {
                    val firebase = FirebaseHelper()
                    val ref = firebase.database!!.child("Cidade")
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (data in p0.children) {
                                val cidade = data.getValue(
                                    Cidade::class.java
                                )!!
                                cidade.id = data.key.toString()
                                lista.add(cidade)
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

    fun selecionarNome(name: Cidade){
        cidade.value = name
    }
    fun selecionarNomeExit(){
        cidade.value = null
    }
}
