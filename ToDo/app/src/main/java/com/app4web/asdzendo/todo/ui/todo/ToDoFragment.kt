package com.app4web.asdzendo.todo.ui.todo

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.databinding.ToDoRecyclerListBinding
import com.app4web.asdzendo.todo.launcher.COUNTSFact
import com.app4web.asdzendo.todo.launcher.PAEMI
import com.app4web.asdzendo.todo.launcher.ToDoInjectorUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A fragment representing a recycler list of Items.
 */
class ToDoFragment : Fragment() {


    private val todoViewModel: ToDoViewModel by viewModels {
        ToDoInjectorUtils.provideToDoViewModelFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Добавляет и обрабатывает меню три точки для этого фрагмента
        setHasOptionsMenu(true)
        Timber.i("ToDo Recycler Fragment onCreate")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        Timber.i("ToDo Recycler Fragment onCreateView")
        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        val binding = ToDoRecyclerListBinding.inflate(inflater, container, false)

        // Вывести в заголовок количество записей в базе не работает - срабатывает из Activity
        todoViewModel.count().observe(viewLifecycleOwner) { count -> activity?.actionBar?.title = "ToDoToDo $count записей" }

        binding.viewmodel = todoViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Говорит можно объявить и в RecyclerView XML
        val manager = LinearLayoutManager(activity)
        binding.recyclerList.layoutManager = manager

        val adapterPage = ToDoPageAdapter(FactListener { factID ->
            todoViewModel.onFactClicked(factID)})

        binding.recyclerList.adapter = adapterPage

        // Подпишите адаптер на ViewModel, чтобы элементы в адаптере обновлялись
        // когда список меняется
        todoViewModel.paemi.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                @OptIn(ExperimentalCoroutinesApi::class)
                todoViewModel.factsPage.collectLatest { adapterPage.submitData(it) }
            }
        }

        todoViewModel.navigateToFactDetail.observe(viewLifecycleOwner) { factID ->
            factID?.let {
                this.findNavController().navigate(
                        ToDoFragmentDirections.actionTodoFragmentToFactDetailFragment(factID,todoViewModel.paemi.value?: PAEMI.N))
                 todoViewModel.navigateToFactDetailNavigated()
            }
        }

        binding.bottomNavView.setOnNavigationItemSelectedListener { paemi ->
            Timber.i("ToDo ToDoFragment  setOnNavigationItemSelectedListener PAEMI $paemi")
            todoViewModel.onClickBottomNavView(paemi)
            }

        return binding.root
    }
    // Добавляет и обрабатывает меню три точки для этого фрагмента
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.to_do, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fact_base_creating -> {
                todoViewModel.addFactDatabase(COUNTSFact) // Дозаполнить заново базу данных
                Toast.makeText(activity,"База добавлено  $COUNTSFact * 7 = ${COUNTSFact * 7} записей ", Toast.LENGTH_SHORT).show()
            }
            R.id.fact_base_clearing -> {
                todoViewModel.clear()
                Toast.makeText(activity,"База очищена ", Toast.LENGTH_SHORT).show()
            }
            else -> return false
        }
        return true
    }
}