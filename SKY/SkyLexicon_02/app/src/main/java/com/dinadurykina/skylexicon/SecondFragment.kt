package com.dinadurykina.skylexicon

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dinadurykina.skylexicon.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private val args: SecondFragmentArgs by navArgs()
    lateinit var binding: FragmentSecondBinding
    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: SecondViewModel by lazy {
        ViewModelProvider(this).get(SecondViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ids.setText(args.id)

        binding.skyImage.setOnClickListener {
            val slovo = viewModel.property.value?.text?: "Table"
            findNavController().navigate(
                SecondFragmentDirections.actionSecondFragmentToFirstFragment(slovo))
        }

        binding.textviewJson.movementMethod = ScrollingMovementMethod()
    }
}