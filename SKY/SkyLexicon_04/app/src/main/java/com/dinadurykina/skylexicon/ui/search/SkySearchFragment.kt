package com.dinadurykina.skylexicon.ui.search

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dinadurykina.skylexicon.databinding.FragmentSkySearchBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SkySearchFragment : Fragment() {
    lateinit var thiscontext: Context
    private val args: SkySearchFragmentArgs by navArgs()
    lateinit var binding: FragmentSkySearchBinding
    lateinit var viewModel: SkySearchViewModel
    /**
     * Lazily initialize our [SkySearchViewMode].
     */
  //  private val viewModel: SkySearchViewModel by lazy {
  //      ViewModelProvider(this).get(SkySearchViewModel::class.java) }

    private val _backup: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>(null)
    val backup: LiveData<Boolean?>
        get() = _backup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (container != null) thiscontext = container.context
         viewModel = ViewModelProvider(
             this,
             SkySearchViewModelFactory(args.slovo)
         ).get(SkySearchViewModel::class.java)

        // Inflate the layout for this fragment
        binding = FragmentSkySearchBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.slovo.setText(args.slovo)
        viewModel.searchSlovo(args.slovo)

        binding.ids.setOnClickListener {
            val id = viewModel.word.value?.meanings?.get(0)?.id?:"1938"
            findNavController().navigate(
                SkySearchFragmentDirections.actionSkySearchFragmentToSkyMeaningsFragment(id)
            )
        }
        binding.textviewJson.movementMethod = ScrollingMovementMethod()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val meanings02Adapter = ArrayAdapter<String?>(
            thiscontext,
            android.R.layout.simple_list_item_1,
            viewModel.meanings02
        )
        binding.meanings02.adapter = meanings02Adapter

        val meanings20Adapter = ArrayAdapter<String?>(
            thiscontext,
            android.R.layout.simple_list_item_1,
            viewModel.meanings20
        )
        binding.meanings20.adapter = meanings20Adapter

        viewModel.refresh.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true. Наблюдаемое состояние истинно.

                binding.meanings02Size.text = " всего переводов: ${viewModel.meanings02.size.toString()} теперь"
                meanings02Adapter.notifyDataSetChanged()

                binding.meanings20Size.text = " всего вариантов: ${viewModel.meanings20.size.toString()} стало"
                meanings20Adapter.notifyDataSetChanged()

              //  meaningsWithSimilarTranslationAdapter.notifyDataSetChanged()
              //  alternativeTranslationsAdapter.notifyDataSetChanged()
              //  imagesAdapter.notifyDataSetChanged()
                viewModel.refreshNull()
            }
        }
        binding.meanings02.setOnItemClickListener { parent, itemClicked, position, id ->
            val slovo = viewModel.words.value?.get(0)?.meanings?.get(position)?.translation?.text
                ?: "Нетути"
            binding.slovo.setText(slovo)
            viewModel.searchSlovo(slovo)
            Toast.makeText(
                thiscontext,
                "Переход с ${(itemClicked as TextView).text} на $slovo",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.meanings20.setOnItemClickListener { parent, itemClicked, position, id ->
            val slovo = viewModel.words.value?.get(position)?.text ?: "NoNoNo"
            binding.slovo.setText(slovo)
            viewModel.searchSlovo(slovo)
            Toast.makeText(
                thiscontext,
                "Переход с ${(itemClicked as TextView).text} на $slovo",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}