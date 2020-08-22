package com.app4web.asdzendo.todo.launcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.databinding.ActivityToDoBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class ToDoActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainBinding: ActivityToDoBinding
    private val mainViewModel: ToDoActitityViewModel  by viewModels {
        ToDoInjectorUtils.provideToDoActitityViewModelFactory(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView (this, R.layout.activity_to_do)
        setSupportActionBar(mainBinding.toolbar)


        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, mainBinding.drawerLayout)
        mainBinding.toolbar.setupWithNavController(navController, appBarConfiguration)
        mainBinding.navView.setupWithNavController(navController)
        //mainBinding.bottomNavView.setupWithNavController(navController) перенесен в фрагмент

        // Вывести в заголовок количество записей в базе
        mainViewModel.count().observe(this) { count -> title = "ToDoDo $count"}

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
        Timber.i("ToDoMainActivity onCreate ")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.to_do, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.fact_base_creating -> {
                mainViewModel.addFactDatabase(COUNTSFact) // Дозаполнить заново базу данных
                Toast.makeText(applicationContext,"База добавлено  ${COUNTSFact} * 7 = ${COUNTSFact * 7} записей ",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.fact_base_clearing -> {
                mainViewModel.clear()
                Toast.makeText(applicationContext,"База очищена ",Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
