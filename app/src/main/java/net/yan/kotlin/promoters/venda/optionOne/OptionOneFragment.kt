package net.yan.kotlin.promoters.venda.optionOne

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import net.yan.kotlin.promoters.R
import net.yan.kotlin.promoters.databinding.FragmentOptionOneBinding
import net.yan.kotlin.promoters.venda.optionOne.adapter.Adapter
import net.yan.kotlin.promoters.venda.optionOne.adapter.ClienteListener

/**
 * A simple [Fragment] subclass.
 */
class OptionOneFragment : Fragment() {

    private lateinit var binding: FragmentOptionOneBinding
    private lateinit var viewModel: OptionOneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_option_one, container, false)
        val viewModelFactory = OptionOneViewModelFactory(resources)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OptionOneViewModel::class.java)

        val adapter = Adapter(ClienteListener { nome ->
            viewModel.selecionarNome(nome)
        })

        viewModel.nomesLista.observe(viewLifecycleOwner, Observer {
            it.let {
                Log.i("NOMES", it.get(0))
                adapter.addHeaderAndSubmitList(it)
            }
        })
        binding.recycler.adapter = adapter

        return binding.root
    }

}
