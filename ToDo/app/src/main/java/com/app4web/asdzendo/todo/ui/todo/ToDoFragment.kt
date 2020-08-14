package com.app4web.asdzendo.todo.ui.todo

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.databinding.ToDoRecyclerListBinding
import com.app4web.asdzendo.todo.ui.ToDoApplication.Companion.FactContentFACTS
import com.app4web.asdzendo.todo.viewmodels.FragmentRecyclerViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber


/**
 * A fragment representing a list of Items.
 */
class ToDoFragment : Fragment() {


    private var orientation: Int = 0
    private val fragmentRecyclerViewModel: FragmentRecyclerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orientation = resources.configuration.orientation

        //val dataSource = FactDatabase.getInstance(this).factDatabaseDao
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val binding : ToDoRecyclerListBinding = DataBindingUtil.inflate(inflater, R.layout.to_do_recycler_list, container, false)

        // Set the adapter На будущее для подклюяения cardView
            with(binding.recyclerList) {
                layoutManager = when (orientation){
                    Configuration.ORIENTATION_PORTRAIT -> LinearLayoutManager(context)
                    Configuration.ORIENTATION_LANDSCAPE -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, orientation)
                }

                // adapter = DummyAdapterList(DummyContent.ITEMS)
                // adapter = FactAdapterList(FactContent.FACTS)
                //adapter = ToDoAdapterList(FactContent.FACTS)
                adapter = ToDoAdapterList(FactContentFACTS)
            }

        binding.viewmodel = fragmentRecyclerViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        fragmentRecyclerViewModel.snackbar.observe(viewLifecycleOwner) { snack ->
            if (snack == true) {
                Snackbar.make(
                        binding.root,
                        "Snackbar через LiveData",
                        Snackbar.LENGTH_LONG)
                    //.setAction("Action", null)
                    .show()
                Timber.i("ToDotimber Snackbar")
                fragmentRecyclerViewModel.snackbarFalse()
            }
        }

        binding.bottomNavView.setOnNavigationItemSelectedListener { paemi ->
            Timber.i("ToDo ToDoFragment  setOnNavigationItemSelectedListener PAEMI $paemi")
            fragmentRecyclerViewModel.onClickBottomNavView(paemi)}

        Timber.i("ToDotimber Fragment")

        return binding.root
    }
}