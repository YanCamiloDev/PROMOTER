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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.model.PromPontos
import net.yan.kotlin.promoters.model.Promoter
import net.yan.kotlin.promoters.model.Venda
import java.io.ByteArrayOutputStream
import java.security.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class VendaViewModel(val resouces: Resources, val dataSource: FirebaseHelper) : ViewModel() {

    val job = Job()
    val coroutine = CoroutineScope(Dispatchers.IO + job)
    val lista = MutableLiveData<Array<Promoter>>()
    val foto = MutableLiveData<Boolean>()
    val verifica =  MutableLiveData<Boolean>()
    val isFim = MutableLiveData<Boolean>()

    fun tirarFoto() {
        foto.value = true
    }

    fun alert(){
        verifica!!.value = true
    }
    fun alertExit(){
        verifica!!.value = false
    }

    fun exit(){
        isFim.value = false
    }

    fun onFire(pontos: PromPontos) {
        coroutine.launch {
            try {
                val prom = PromPontos()
                val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                val date = Date()
                val strDate: String = dateFormat.format(date).toString()
                prom.fk_id_promoter = dataSource.auth!!.currentUser!!.uid
                prom.fk_id_cidade = pontos.fk_id_cidade
                prom.fk_id_pontos = pontos.fk_id_pontos
                prom.data = strDate
                prom.foto = pontos.foto
                val ref =
                    dataSource.database!!.child("Promoter_Ponto").push().child(prom.fk_id_promoter)
                        .setValue(prom)
                ref.addOnCompleteListener {
                    alertExit()
                    if (it.isSuccessful) {
                        isFim.value = true
                    }
                }
            } catch (e: FirebaseException) {
                Log.i("ERRO", e.message)
            }
        }
    }


    fun gravarFoto(cidade: String, cliente: String,  imageBitmap: Bitmap) {
        alert()
        coroutine.launch {
            try {
                val nomeImagem = UUID.randomUUID().toString()
                val storageRef =
                    FirebaseStorage.getInstance().reference.child("imagens").child("vendas").child(
                        "${nomeImagem}.jpeg"
                    )
                val baos = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
                val data = baos.toByteArray()

                var uploadTask = storageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    Log.i("FALHOU", it.message)
                }.addOnSuccessListener {
                    val dow = storageRef.downloadUrl
                    dow.addOnSuccessListener {
                        val link = it.toString()
                        val p = PromPontos()
                        p.foto = link
                        p.fk_id_pontos = cliente
                        p.fk_id_cidade = cidade
                        onFire(p)
                    }
                }
            } catch (e: FirebaseException) {
                Log.i("Exception", "UPLOAD FOTO " + e.message)
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