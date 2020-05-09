package net.yan.kotlin.promoters.venda.optionTwo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

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
        val viewModelFactory = OptionTwoViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OptionTwoViewModel::class.java)


        return binding.root
    }

}
