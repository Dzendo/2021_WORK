package com.app4web.asdzendo.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.database.FactDatabase
import com.app4web.asdzendo.todo.databinding.FactDetailFragmentBinding
import com.app4web.asdzendo.todo.viewmodels.FactDetailViewModel
import com.app4web.asdzendo.todo.viewmodels.FactDetailViewModelFactory

class FactDetailFragment : Fragment() {

    companion object {
        fun newInstance() = FactDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        val binding: FactDetailFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fact_detail_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = FactDetailFragmentArgs.fromBundle(requireArguments())

        // Create an instance of the ViewModel Factory.
        // Создайте экземпляр фабрики ViewModel.
        val dataSource = FactDatabase.getInstance(application).factDatabaseDao
        val viewModelFactory = FactDetailViewModelFactory(arguments.factID, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        // Получить ссылку на ViewModel, связанную с этим фрагментом.
        val factDetailViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(FactDetailViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, вы должны явно
        // дайте объекту привязки ссылку на него.
        binding.factDetailViewModel = factDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner //this



        return binding.root
    }

   /* override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FactDetailViewModel::class.java)
    }*/

}