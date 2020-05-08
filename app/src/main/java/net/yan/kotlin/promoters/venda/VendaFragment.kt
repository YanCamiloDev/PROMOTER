package net.yan.kotlin.promoters.venda

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import net.yan.kotlin.promoters.R
import net.yan.kotlin.promoters.databinding.FragmentVendaBinding

/**
 * A simple [Fragment] subclass.
 */
class VendaFragment : Fragment() {
    private lateinit var binding: FragmentVendaBinding
    private lateinit var viewModel: VendaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_venda, container, false)
        setHasOptionsMenu(true)
        binding.setLifecycleOwner(this)
        val viewModelFactory = VendaViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VendaViewModel::class.java)
        binding.viewModel = viewModel
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
        binding.toolbar.setTitle("")
        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val drawable = BitmapDrawable(resources, imageBitmap)
            binding.appBar.background = drawable
        }
    }
}
