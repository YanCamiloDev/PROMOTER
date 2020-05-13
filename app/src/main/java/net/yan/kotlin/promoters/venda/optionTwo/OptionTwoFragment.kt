package net.yan.kotlin.promoters.venda.optionTwo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar

import net.yan.kotlin.promoters.R
import net.yan.kotlin.promoters.databinding.OptionTwoFragmentBinding

class OptionTwoFragment : Fragment() {


    private lateinit var viewModel: OptionTwoViewModel
    private lateinit var binding: OptionTwoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.option_two_fragment, container, false)
        val viewModelFactory = OptionTwoViewModelFactory(resources)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OptionTwoViewModel::class.java)
        val argments by navArgs<OptionTwoFragmentArgs>()
        Snackbar.make(binding.root, argments.cliente.toString(), Snackbar.LENGTH_LONG).show()
        val adapter = AdapterTwo(Clique {
            viewModel.selecionarNome(it)
        })
        viewModel.nomesLista.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.adicionarLista(it)
            }
        })
        viewModel.cidade.observe(viewLifecycleOwner, Observer {
            if (it != null){
                findNavController().navigate(OptionTwoFragmentDirections.actionOptionTwoFragmentToVendaFragment(it.id, argments.cliente))
                viewModel.selecionarNomeExit()
            }
        })
        binding.search2.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        binding.recycler2.adapter = adapter
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}
