package net.yan.kotlin.promoters.principal

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar

import net.yan.kotlin.promoters.R
import net.yan.kotlin.promoters.data.FirebaseHelper
import net.yan.kotlin.promoters.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        val firebase = FirebaseHelper()
        val viewModelFactory = HomeViewModelFactory(firebase, binding)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.estaLogado.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        })

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    } else {
                        activity?.finish()
                    }
                }
            })

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel


        val linear = binding.bottomDrawer
        bottomSheetBehavior = BottomSheetBehavior.from(linear)
        binding.bar.setNavigationOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
        binding.bar.setNavigationIcon(R.drawable.ic_menu_black_24dp)


        viewModel.newPhoto.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToOptionOneFragment())
                viewModel.addLocalAndVendaClose()
            }

        })
        return binding.root
    }


}
