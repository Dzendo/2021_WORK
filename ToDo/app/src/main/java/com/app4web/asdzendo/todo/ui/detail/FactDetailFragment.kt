package com.app4web.asdzendo.todo.ui.detail


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app4web.asdzendo.todo.databinding.FactDetailFragmentBinding
import com.app4web.asdzendo.todo.launcher.ToDoInjectorUtils
import timber.log.Timber
import com.app4web.asdzendo.todo.R


class FactDetailFragment : Fragment() {

    private val args: FactDetailFragmentArgs by navArgs()

    private val factDetailViewModel: FactDetailViewModel by viewModels {
        ToDoInjectorUtils.provideFactDetailViewModelFactory(requireContext(), args.factID, args.paemi)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Добавляет и обрабатывает меню три точки для этого фрагмента
        setHasOptionsMenu(true)
        Timber.i("ToDo FactDetailFragment onCreate ")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        Timber.i("ToDoFactDetailFragment onCreateView ")

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.

        val binding = FactDetailFragmentBinding.inflate(inflater, container, false)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, вы должны явно
        // дайте объекту привязки ссылку на него.
        binding.viewmodel = factDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner //this
       // binding.fact = factDetailViewModel.getFact().value

        // Для возврата в таблицу по нажатию любой кнопки
        factDetailViewModel.backup.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true. Наблюдаемое состояние истинно.
                this.findNavController().navigateUp()
                factDetailViewModel.backupNull()
            }
        }
        return binding.root
    }
    // Добавляет и обрабатывает меню три точки для этого фрагмента
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Add_fact ->
                factDetailViewModel.insert()
            R.id.Update_fact->
                factDetailViewModel.update()
            R.id.Delete_fact->
                factDetailViewModel.delete()
            else -> return false
        }
        return true
    }
}