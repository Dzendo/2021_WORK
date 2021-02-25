package com.dinadurykina.skylexicon.ui.search

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dinadurykina.skylexicon.databinding.FragmentSkySearchBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SkySearchFragment : Fragment() {
    private val args: SkySearchFragmentArgs by navArgs()
    lateinit var binding: FragmentSkySearchBinding
    /**
     * Lazily initialize our [SkySearchViewMode].
     */
    private val viewModel: SkySearchViewModel by lazy {
        ViewModelProvider(this).get(SkySearchViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentSkySearchBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.slovo.setText(args.slovo)

        binding.skyImage.setOnClickListener {
            val id = viewModel.property.value?.meanings?.get(0)?.id?:"1938"
            findNavController().navigate(
                SkySearchFragmentDirections.actionSkySearchFragmentToSkyMeaningsFragment(id))
        }
        binding.textviewJson.movementMethod = ScrollingMovementMethod()
    }
}