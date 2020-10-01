package com.app4web.asdzendo.todo.launcher


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.app4web.asdzendo.todo.R
import com.app4web.asdzendo.todo.databinding.ActivityToDoBinding
import timber.log.Timber


class ToDoActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainBinding: ActivityToDoBinding
    private val mainViewModel: ToDoActitityViewModel by viewModels {
        ToDoInjectorUtils.provideToDoActitityViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_to_do)
        // Добавляет и обрабатывает меню три точки для этого фрагмента
        setSupportActionBar(mainBinding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, mainBinding.drawerLayout)
        mainBinding.toolbar.setupWithNavController(navController, appBarConfiguration)
        mainBinding.navView.setupWithNavController(navController)
        //mainBinding.bottomNavView.setupWithNavController(navController) перенесен в фрагмент

        // Вывести в заголовок количество записей в базе
        val bas = if (BASE_IN_MEMORY) "M" else "D"

        mainViewModel.count().observe(this) { count -> title = "ToDo$bas=$count" }

        mainBinding.viewmodel = mainViewModel
        mainBinding.lifecycleOwner = this

        Timber.i("ToDoMainActivity onCreate ")
    }

    // Добавляет и обрабатывает меню три точки для этого фрагмента
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.action_reboot -> startActivity(Intent(this, ToDoActivity::class.java))

            //R.id.main_action_base ->  BASE_IN_MEMORY = false
            R.id.main_action_base_in_memory -> BASE_IN_MEMORY = true
            R.id.main_action_base_on_disk -> BASE_IN_MEMORY = false

            R.id.main_action_constfact_1 -> COUNTSFact = 1
            R.id.main_action_constfact_10 -> COUNTSFact = 10
            R.id.main_action_constfact_100 -> COUNTSFact = 100
            R.id.main_action_constfact_1000 -> COUNTSFact = 1_000
            R.id.main_action_constfact_10_000 -> COUNTSFact = 10_000
            R.id.main_action_constfact_100_000 -> COUNTSFact = 100_000
            R.id.main_action_constfact_1000_000 -> COUNTSFact = 1_000_000
            else -> {
                super.onOptionsItemSelected(item); return false
            }
        }
        Toast.makeText(applicationContext, "base in memory = $BASE_IN_MEMORY COUNTSFact = $COUNTSFact", Toast.LENGTH_LONG).show()
        return true
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putInt("COUNTSFact", COUNTSFact)
        savedInstanceState.putBoolean("BASE_IN_MEMORY", BASE_IN_MEMORY);
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        COUNTSFact = savedInstanceState.getInt("COUNTSFact")
        BASE_IN_MEMORY = savedInstanceState.getBoolean("BASE_IN_MEMORY");
    }
}

/* вроде нужен для возврата по стрелке но работает и без него т.к. есть app:defaultNavHost="true"
    override fun onSupportNavigateUp(): Boolean {
    //  = findNavController(R.id.nav_host_fragment).navigateUp()
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
