package com.app4web.asdzendo.todo.ui.todo

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.database.FactDatabase
import com.app4web.asdzendo.todo.databinding.ActivityToDoBinding
import com.app4web.asdzendo.todo.databinding.ToDoRecyclerListBinding
import com.app4web.asdzendo.todo.ui.todo.dummy.DummyAdapterList
import com.app4web.asdzendo.todo.ui.todo.dummy.DummyContent
import com.app4web.asdzendo.todo.ui.todo.dummy.FactAdapterList
import com.app4web.asdzendo.todo.ui.todo.dummy.FactContent
import com.app4web.asdzendo.todo.viewmodels.FragmentRecyclerViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.to_do_recycler_list.view.*
import timber.log.Timber


/**
 * A fragment representing a list of Items.
 */
class ToDoFragment : Fragment() {

    private var PAEMI: Char = 'A'

    private var orientation: Int = 3
    private val fragmentRecyclerViewModel: FragmentRecyclerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orientation = resources.configuration.orientation

        //val dataSource = FactDatabase.getInstance(this).factDatabaseDao
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.to_do_recycler_list, container, false)
        val binding : ToDoRecyclerListBinding = DataBindingUtil.inflate(inflater, R.layout.to_do_recycler_list, container, false)

        // Set the adapter
       //if (view is RecyclerView) {
            with(binding.recyclerList) {
                layoutManager = when (orientation){
                    Configuration.ORIENTATION_PORTRAIT -> LinearLayoutManager(context)
                    Configuration.ORIENTATION_LANDSCAPE -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, orientation)
                }
                // adapter = DummyAdapterList(DummyContent.ITEMS)
                // adapter = FactAdapterList(FactContent.FACTS)
                adapter = ToDoAdapterList(FactContent.FACTS)
            }
        //}
        binding.viewmodel = fragmentRecyclerViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        fragmentRecyclerViewModel.snackbar.observe(viewLifecycleOwner) {snack ->
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
        BottomNavigationView.OnNavigationItemSelectedListener { paemi ->
            Timber.i("ToDotimber PAEMI")
            true
            when(paemi.itemId) {
                R.id.ideas -> { PAEMI = 'I' ; true}
                R.id.plan -> { PAEMI = 'P' ; true}
                R.id.action -> { PAEMI = 'A' ; true}
                R.id.event -> { PAEMI = 'E' ; true}
                R.id.money -> { PAEMI = 'M' ; true}
                else -> false

            }
        }



        Timber.i("ToDotimber Fragment")

        return binding.root
    }
}