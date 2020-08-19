package com.app4web.asdzendo.todo.ui.todo

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app4web.asdzendo.todo.database.FactDatabase
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import com.app4web.asdzendo.todo.databinding.ToDoRecyclerListBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

/**
 * A fragment representing a list of Items.
 */
class ToDoFragment : Fragment() {

    private var orientation: Int = 0
    //private val ToDoViewModel: ToDoViewModel by viewModels()
    // private lateinit var  binding: FactDetailFragmentBinding
    private lateinit var  application: Application
    private lateinit var  dataSource: FactDatabaseDao
    private lateinit var  viewModelFactory: ToDoViewModelFactory
    private lateinit var  todoViewModel: ToDoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("ToDo Recycler Fragment onCreate")
        orientation = resources.configuration.orientation
        application = requireNotNull(this.activity).application
        dataSource = FactDatabase.getInstance(application).factDatabaseDao
        viewModelFactory = ToDoViewModelFactory(dataSource, application)
        todoViewModel =
             ViewModelProvider(this, viewModelFactory).get(ToDoViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        Timber.i("ToDo Recycler Fragment onCreateView")
        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        val binding = ToDoRecyclerListBinding.inflate(inflater, container, false)

        binding.viewmodel = todoViewModel

        // Говорит можно объявить и в RecyclerView XML
        val manager = LinearLayoutManager(activity)
        binding.recyclerList.layoutManager = manager

        // Говорит можно объявить и в RecyclerView XML
        val adapter = ToDoAdapterList(FactListener { factID ->
            //todoViewModel.onFactClicked(factId)
            this.findNavController().navigate(
                    ToDoFragmentDirections.actionTodoFragmentToFactDetailFragment(factID))
          //  Toast.makeText(context,  " Тырк в строку $factId", Toast.LENGTH_LONG).show()
        })
        binding.recyclerList.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner

        todoViewModel.facts.observe(viewLifecycleOwner) {
            it?.let {
                //adapter.submitList(it)
                adapter.addHeaderAndSubmitList(it)
                // 23.10 Наконец, обновите, SleepTrackerFragment чтобы передать список DataItem вместо списка SleepNight
                // и вызвать новый метод addHeaderAndSubmitList вместо метода submitList:
                // LiveData observers are sometimes passed null, so make sure you check for null.
                // Наблюдатели живых данных иногда передаются null, поэтому убедитесь, что вы проверяете наличие null.
                //adapter.refreshUsers()
                //binding.executePendingBindings()  // попоросить привязку выполнить обновление сразу
            }
        }

        todoViewModel.snackbar.observe(viewLifecycleOwner) { snack ->
            if (snack == true) {
                Snackbar.make(
                        binding.root,
                        "Snackbar через LiveData",
                        Snackbar.LENGTH_LONG)
                    //.setAction("Action", null)
                    .show()
                Timber.i("ToDotimber Snackbar")
                todoViewModel.snackbarFalse()
            }
        }

        binding.bottomNavView.setOnNavigationItemSelectedListener { paemi ->
            Timber.i("ToDo ToDoFragment  setOnNavigationItemSelectedListener PAEMI $paemi")
            val returnClik = todoViewModel.onClickBottomNavView(paemi)
           // adapter.addHeaderAndSubmitList(todoViewModel.facts.value)
           // adapter.refreshUsers()
           // binding.executePendingBindings()  // попоросить привязку выполнить обновление сразу
            returnClik
            }
        return binding.root
    }
}