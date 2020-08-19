package com.app4web.asdzendo.todo.ui.detail

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app4web.asdzendo.todo.database.FactDatabase
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import com.app4web.asdzendo.todo.databinding.FactDetailFragmentBinding
import timber.log.Timber

class FactDetailFragment : Fragment() {
   // private lateinit var  binding: FactDetailFragmentBinding
    private lateinit var  application: Application
    private lateinit var  arguments: FactDetailFragmentArgs
    private lateinit var  dataSource: FactDatabaseDao
    private lateinit var  viewModelFactory: FactDetailViewModelFactory
    private lateinit var  factDetailViewModel: FactDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = requireNotNull(this.activity).application
        arguments = FactDetailFragmentArgs.fromBundle(requireArguments())
        dataSource = FactDatabase.getInstance(application).factDatabaseDao
        viewModelFactory = FactDetailViewModelFactory(arguments.factID, dataSource)
        factDetailViewModel =
            ViewModelProvider(this, viewModelFactory).get(FactDetailViewModel::class.java)
        Timber.i("ToDo FactDetailFragment onCreate ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("ToDoFactDetailFragment onCreateView ")

        // Get a reference to the binding object and inflate the fragment views.
        // Получить ссылку на объект привязки и раздуть представления фрагментов.
        //binding = DataBindingUtil.inflate(
        //inflater, R.layout.fact_detail_fragment, container, false)
        val binding = FactDetailFragmentBinding.inflate(inflater, container, false)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, вы должны явно
        // дайте объекту привязки ссылку на него.
        binding.factDetailViewModel = factDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner //this

        return binding.root
    }
}