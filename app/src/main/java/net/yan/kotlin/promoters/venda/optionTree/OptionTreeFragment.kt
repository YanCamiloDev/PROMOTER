package net.yan.kotlin.promoters.venda.optionTree

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import net.yan.kotlin.promoters.R
import net.yan.kotlin.promoters.databinding.OptionTreeFragmentBinding
import net.yan.kotlin.promoters.venda.optionOne.adapter.Adapter
import net.yan.kotlin.promoters.venda.optionOne.adapter.ClienteListener

class OptionTreeFragment : Fragment() {


    private lateinit var viewModel: OptionTreeViewModel
    private lateinit var binding: OptionTreeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.option_tree_fragment, container, false)
        val viewModelFactory = OptionTreeViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OptionTreeViewModel::class.java)


        return binding.root
    }

}
