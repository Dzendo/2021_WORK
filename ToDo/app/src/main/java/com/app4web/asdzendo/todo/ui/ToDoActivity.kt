package com.app4web.asdzendo.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.databinding.ActivityToDoBinding
import com.app4web.asdzendo.todo.viewmodels.ToDoActitityViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class ToDoActivity : AppCompatActivity() {
    //private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel: ToDoActitityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = DataBindingUtil.setContentView<ActivityToDoBinding> (this, R.layout.activity_to_do)
        setSupportActionBar(mainBinding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph, mainBinding.drawerLayout)
        mainBinding.toolbar.setupWithNavController(navController, appBarConfiguration)
        mainBinding.navView.setupWithNavController(navController)
        mainBinding.bottomNavView.setupWithNavController(navController)

        mainBinding.viewmodel = mainViewModel
        mainBinding.lifecycleOwner = this


        mainViewModel.snackbar.observe(this) {snack ->
            if (snack == true) {
                Snackbar.make(
                    mainBinding.root,
                    "Snackbar через LiveData",
                    Snackbar.LENGTH_LONG)
                    //.setAction("Action", null)
                    .show()
                mainViewModel.snackbarFalse()
            }
        }
        Timber.i("PAEMItimber MainActivity")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.to_do, menu)
        return true
    }

}

/* вроде нужен для возврата по стрелке но работает и без него т.к. есть app:defaultNavHost="true"
    override fun onSupportNavigateUp(): Boolean {
    //  = findNavController(R.id.nav_host_fragment).navigateUp()
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/

// Passing each menu ID as a set of Ids because each
// menu should be considered as top level destinations.
// Передача каждого идентификатора меню в виде набора идентификаторов, поскольку каждый
// меню следует рассматривать как пункты назначения верхнего уровня. (onCreate)
//appBarConfiguration = AppBarConfiguration(setOf(
//        R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
