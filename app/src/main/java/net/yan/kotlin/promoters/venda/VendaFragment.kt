package net.yan.kotlin.promoters.venda

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.SelectionTracker.SelectionPredicate
import androidx.recyclerview.selection.StorageStrategy
import com.google.android.material.snackbar.Snackbar
import net.yan.kotlin.promoters.R
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.databinding.FragmentVendaBinding
import net.yan.kotlin.promoters.model.Promoter


/**
 * A simple [Fragment] subclass.
 */
class VendaFragment : Fragment() {
    private lateinit var binding: FragmentVendaBinding
    private lateinit var viewModel: VendaViewModel
    private lateinit var firebaseHelper: FirebaseHelper
    private var selectionTracker: SelectionTracker<Long>? = null
    private var lista : Array<Promoter>? = null
    private var selectPromotor: Promoter? = null
    private var verificador: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_venda, container, false)
        setHasOptionsMenu(true)
        binding.setLifecycleOwner(this)
        firebaseHelper = FirebaseHelper()
        val viewModelFactory = VendaViewModelFactory(resources, firebaseHelper)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VendaViewModel::class.java)
        binding.viewModel = viewModel
        binding.floating.hide()
        val arguments by navArgs<VendaFragmentArgs>()
        binding.cidade = arguments.cidade
        binding.cliente = arguments.cliente
        binding.floating.setOnClickListener {
            if (verificador && binding.foto != null)
                viewModel.onFire(binding.cidade.toString(), binding.cliente.toString(), selectPromotor!!, binding.foto as Bitmap)
            else
                Snackbar.make(binding.root, "ADD A FOTO E SELECIONE O PROMOTOR", Snackbar.LENGTH_LONG).show()
        }
        val adapter = AdapterVenda(Clique {

        })
        viewModel.lista.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.adicionarLista(it)
                lista = it
            }
        })

        binding.searchProm.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        binding.rec.adapter = adapter
        viewModel.foto.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                        startActivityForResult(takePictureIntent, 100)
                    }
                }
                viewModel.tirarFotoClose()
            }
        })

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        selectionTracker = SelectionTracker.Builder<Long>(
            "mySelection",
            binding.rec,
            AdapterVenda.KeyProvider(adapter),
            AdapterVenda.DetailsLookup(binding.rec),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            object : SelectionPredicate<Long>() {
                override fun canSetStateForKey(
                    key: Long,
                    nextState: Boolean
                ): Boolean {
                    return true
                }
                override fun canSetStateAtPosition(
                    position: Int,
                    nextState: Boolean
                ): Boolean {
                    return true
                }
                override fun canSelectMultiple(): Boolean {
                    return false // Set to false to allow single selecting
                }
            }
        ).build()
        adapter.setSelection(selectionTracker)

        selectionTracker!!.addObserver(
            object : SelectionTracker.SelectionObserver<Long?>() {
                override fun onItemStateChanged(key: Long, selected: Boolean) {
                    super.onItemStateChanged(key, selected)
                    if (selected){
                        selectPromotor = lista?.get(key.toInt())
                        binding.promotor = selectPromotor
                        binding.floating.show()
                    }else{
                        binding.promotor = null
                        selectPromotor = null
                        binding.floating.hide()
                    }
                }
                override fun onSelectionChanged() {

                }
            })

        binding.toolbar.setTitle("")
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val drawable = BitmapDrawable(resources, imageBitmap)
            verificador = true
            binding.foto = imageBitmap
            binding.appBar.background = drawable
        }else{
            binding.foto = null
            verificador = false
        }
    }
}