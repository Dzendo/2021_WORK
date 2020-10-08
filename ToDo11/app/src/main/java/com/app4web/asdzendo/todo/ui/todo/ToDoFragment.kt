package com.app4web.asdzendo.todo.ui.todo

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.databinding.ToDoRecyclerListBinding
import com.app4web.asdzendo.todo.launcher.COUNTSFact
import com.app4web.asdzendo.todo.launcher.PAEMI
import com.app4web.asdzendo.todo.launcher.ToDoInjectorUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A fragment representing a recycler list of Items.
 */
class ToDoFragment : Fragment() {


    private val todoViewModel: ToDoViewModel by viewModels {
        ToDoInjectorUtils.provideToDoViewModelFactory(requireContext())
    }
   // init {
        var paemiJob: Job? = null
        val toDoFragmentJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + toDoFragmentJob)
        // Coroutine that will be canceled when the ViewModel is cleared.

       // Timber.i("ToDoViewModel created PAEMI= ${paemi.value?.name}")
   // }

    var iscancelFlow = false

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

        binding.viewmodel = todoViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerList.adapter = todoViewModel.adapterPage

        todoViewModel.paemi.observe(viewLifecycleOwner) { paemi: PAEMI? ->
              paemiJob?.cancel()            // стартует показывает не отменяет
              paemiJob = uiScope.launch {   // стартует показывает не отменяет
           // paemiJob = viewLifecycleOwner.lifecycleScope.launch {   // стартует показывает не отменяет
                todoViewModel.factsPageChange()
            }
        }

        // Он наблюдает когда поступит команда перехода на форму detail (тап в строчку или fab)
        //
        todoViewModel.navigateToFactDetail.observe(viewLifecycleOwner) { factID ->
            factID?.let {
                // Получив команду перейти мы зовем NavController это то же самое, что NavHostFragment
                // Мы ему говорим пошли, навигируй нас в actionTodoFragmentToFactDetailFragment
                // Он ищет это в mobile_navigation, который он тогда еще счита и помнит
                // Он находит: <action
                //            android:id="@+id/action_todoFragment_to_factDetailFragment"
                //            app:destination="@id/factDetailFragment" />
                // (подчерки и малые и большие буквы, это правило обращения ToDoFragmentDirections -
                // пакета безопасной навигации из архитектуры)
                // Указано куда переходить к фрагменту с именем factDetailFragment в файле mobile_navigation
                // А там <fragment
                //        android:id="@+id/factDetailFragment"
                //        android:name="com.app4web.asdzendo.todo.ui.detail.FactDetailFragment"
                // соответственно зовет FactDetailFragment.kt из указанного каталога
                // и говорит ему ты сюда давай размещайся и отдает ему управление
                // но еще здесь он ему посылает два аргумента factID и paemi (как указано в mobile_navigation)
                this.findNavController().navigate(
                        ToDoFragmentDirections.actionTodoFragmentToFactDetailFragment(factID,todoViewModel.paemi.value?: PAEMI.N))
                 todoViewModel.navigateToFactDetailNavigated()
            }
        }

        return binding.root
    }
    // Добавляет и обрабатывает меню три точки для этого фрагмента
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.to_do, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    // Добавляет и обрабатывает меню три точки для этого фрагмента
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fact_base_creating -> {
                todoViewModel.addFactDatabase(COUNTSFact) // Дозаполнить заново базу данных
                Toast.makeText(activity,"База добавлено  $COUNTSFact * 7 = ${COUNTSFact * 7} записей ", Toast.LENGTH_SHORT).show()
            }
            R.id.fact_base_clearing -> {
                todoViewModel.clear()
                Toast.makeText(activity,"База очищена ", Toast.LENGTH_SHORT).show()
                Timber.i("ToDoFactRepository fact_base_clearing База очищена  ")
            }
            R.id.isCancel -> {
                iscancelFlow = true
                Toast.makeText(activity,"Отмена ", Toast.LENGTH_SHORT).show()
            }
            else -> return false
        }
        return true
    }
}