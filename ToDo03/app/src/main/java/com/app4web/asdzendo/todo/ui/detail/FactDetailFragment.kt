package com.app4web.asdzendo.todo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import androidx.navigation.fragment.navArgs

import com.app4web.asdzendo.todo.databinding.FactDetailFragmentBinding
import com.app4web.asdzendo.todo.launcher.ToDoInjectorUtils

import timber.log.Timber

class FactDetailFragment : Fragment() {

    private val args: FactDetailFragmentArgs by navArgs()

    private val factDetailViewModel: FactDetailViewModel by viewModels {
        ToDoInjectorUtils.provideFactDetailViewModelFactory(requireContext(),args.factID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.i("ToDo FactDetailFragment onCreate ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("ToDoFactDetailFragment onCreateView ")

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.

        val binding = FactDetailFragmentBinding.inflate(inflater, container, false)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, вы должны явно
        // дайте объекту привязки ссылку на него.
        binding.factDetailViewModel = factDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner //this

        // Для возврата в таблицу по нажатию любой кнопки
        factDetailViewModel.backup.observe(viewLifecycleOwner) {
            if (it == true) { // Observed state is true. Наблюдаемое состояние истинно.
                this.findNavController().navigateUp()
                factDetailViewModel.backupNull()
            }
        }
        return binding.root
    }
}