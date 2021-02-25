package com.dinadurykina.skylexicon.ui.meanings

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dinadurykina.skylexicon.databinding.FragmentSkyMeaningsBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SkyMeaningsFragment : Fragment() {
    lateinit var thiscontext: Context
    private val args: SkyMeaningsFragmentArgs by navArgs()
    lateinit var binding: FragmentSkyMeaningsBinding
    lateinit var viewModel: SkyMeaningsViewModel
    /**
     * Lazily initialize our [SkyMeaningsViewModel].
     */
   // private val viewModel: SkyMeaningsViewModel by lazy {
   //     ViewModelProvider(this).get(SkyMeaningsViewModel::class.java)}

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        if (container != null) thiscontext = container.context

       viewModel = ViewModelProvider(
            this,
            SkyMeaningsViewModelFactory(args.id)
        ).get(SkyMeaningsViewModel::class.java)
        // Inflate the layout for this fragment
        binding = FragmentSkyMeaningsBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding.meaning = viewModel.meaning.value

        binding.ids.setText(args.id)
        viewModel.meaningsIds(args.id)

        binding.slovo.setOnClickListener {
            val slovo = viewModel.meaning.value?.text?: "Table"
            findNavController().navigate(
                SkyMeaningsFragmentDirections.actionSkyMeaningsFragmentToSkySearchFragment(slovo))
        }

        binding.textviewJson.movementMethod = ScrollingMovementMethod()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val examplesAdapter = ArrayAdapter<String?>(thiscontext, android.R.layout.simple_list_item_1, viewModel.examples)
        binding.examples.adapter = examplesAdapter

        val meaningsWithSimilarTranslationAdapter = ArrayAdapter<String?>(thiscontext, android.R.layout.simple_list_item_1, viewModel.meaningsWithSimilarTranslation)
        binding.meaningsWithSimilarTranslation.adapter = meaningsWithSimilarTranslationAdapter

        val alternativeTranslationsAdapter = ArrayAdapter<String?>(thiscontext, android.R.layout.simple_list_item_1, viewModel.alternativeTranslations)
        binding.alternativeTranslations.adapter = alternativeTranslationsAdapter

        val imagesAdapter = ArrayAdapter<String?>(thiscontext, android.R.layout.simple_list_item_1, viewModel.images)
        binding.images.adapter = imagesAdapter

        viewModel.refresh.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true. Наблюдаемое состояние истинно.

                examplesAdapter.notifyDataSetChanged()
                meaningsWithSimilarTranslationAdapter.notifyDataSetChanged()
                alternativeTranslationsAdapter.notifyDataSetChanged()
                imagesAdapter.notifyDataSetChanged()
                viewModel.refreshNull()
            }
        }
        binding.alternativeTranslations.setOnItemClickListener { parent, itemClicked, position, id ->
            val slovo = viewModel.meanings.value?.get(0)?.alternativeTranslations?.get(position)?.text ?: "NoNoNo"
            findNavController().navigate(
                SkyMeaningsFragmentDirections.actionSkyMeaningsFragmentToSkySearchFragment(slovo))
        }
        binding.meaningsWithSimilarTranslation.setOnItemClickListener { parent, itemClicked, position, id ->
            val ids = viewModel.meanings.value?.get(0)?.meaningsWithSimilarTranslation?.get(position)?.meaningId.toString() ?: "NoNoNo"
            binding.ids.setText(ids)
            viewModel.meaningsIds(ids)
            Toast.makeText(
                thiscontext,
                "Переход с ${(itemClicked as TextView).text} на $ids",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}