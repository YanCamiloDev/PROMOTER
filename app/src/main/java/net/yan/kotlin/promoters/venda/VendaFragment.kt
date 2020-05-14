package net.yan.kotlin.promoters.venda

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.DialogFragmentNavigator
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
    private var foto: Bitmap? = null
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

        val arguments by navArgs<VendaFragmentArgs>()
        binding.cidadeNome = arguments.cidadeNome
        binding.clienteNome = arguments.clienteNome
        binding.cadastrar.setOnClickListener {
            if (verificador == true && foto != null) {
                viewModel.gravarFoto(
                    arguments.cidade.toString(), arguments.cliente.toString(),
                    foto!!
                )
            } else {
                Snackbar.make(binding.root, "Tire uma foto do local", Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.lista.observe(viewLifecycleOwner, Observer {
            it.let {

            }
        })
        viewModel.verifica.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.loadingSpinner.visibility = View.VISIBLE
            }
        })

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

        viewModel.isFim.observe(viewLifecycleOwner, Observer {
            if (it == true){
                findNavController().navigate(VendaFragmentDirections.actionVendaFragmentToHomeFragment())
            }
        })

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val drawable = BitmapDrawable(resources, imageBitmap)
            verificador = true
            binding.im.setImageBitmap(imageBitmap)
            foto = imageBitmap
        } else {
            foto = null
            verificador = false
        }
    }
}