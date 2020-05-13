package net.yan.kotlin.promoters.venda

import android.content.res.Resources
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.model.PromPontos
import net.yan.kotlin.promoters.model.Promoter
import net.yan.kotlin.promoters.model.Venda
import java.io.ByteArrayOutputStream
import java.util.*

class VendaViewModel(val resouces: Resources, val dataSource: FirebaseHelper) : ViewModel() {

    val job = Job()
    val coroutine = CoroutineScope(Dispatchers.IO + job)
    val lista = MutableLiveData<Array<Promoter>>()
    val foto = MutableLiveData<Boolean>()
    val listaTemp = mutableListOf<Promoter>()


    init {
        coroutine.launch {
            try {
                val ref = dataSource.database!!.child("Promoter")
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (dados in p0.children) {
                            val data = dados.getValue(Promoter::class.java)
                            data?.id = dados.key.toString()
                            listaTemp.add(data!!)
                        }
                        lista.value = listaTemp.toTypedArray()
                    }

                })
            } catch (e: FirebaseException) {
                Log.i("ERRO", e.message)
            }
        }
    }

    fun tirarFoto() {
        foto.value = true
    }

    fun onFire(cidade: String, cliente: String, promoter: Promoter, foto: Bitmap) {
        coroutine.launch {
            try {
                val prom = PromPontos()
                prom.fk_id_cidade = cidade
                prom.fk_id_pontos = cliente
                prom.data = Date().time
                val ref =
                    dataSource.database!!.child("Promoter_Ponto").push().child(promoter.id)
                        .setValue(prom)
                ref.addOnCompleteListener {
                    if (it.isSuccessful) {

                    }
                }
            } catch (e: FirebaseException) {
                Log.i("ERRO", e.message)
            }
        }
    }


    fun gravarFoto(venda: Venda, imageBitmap: Bitmap) {
        coroutine.launch {
            try {
                val storageRef =
                    FirebaseStorage.getInstance().reference.child("imagens").child("vendas").child(
                        "${venda.fk_id_prom_pontos}.jpeg"
                    )

                val baos = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
                val data = baos.toByteArray()

                var uploadTask = storageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    Log.i("FALHOU", it.message)
                }.addOnSuccessListener {
                    val vendaa = venda
                    val dow = storageRef.downloadUrl
                    dow.addOnSuccessListener {
                        vendaa.foto = it.toString()
                        salvarNoBanco(venda)
                    }
                }
            } catch (e: FirebaseException) {
                Log.i("Exception", "UPLOAD FOTO " + e.message)
            }
        }
    }

    fun salvarNoBanco(venda: Venda) {
        coroutine.launch {
            try {
                val ref = dataSource.database!!.child("Promoter").push().setValue(venda)
                ref.addOnCompleteListener {
                    if (it.isSuccessful) {

                    }
                }
            } catch (e: FirebaseException) {
                Log.i("Erro", "SALVAR NO BANCO")
            }
        }
    }

    fun tirarFotoClose() {
        foto.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}