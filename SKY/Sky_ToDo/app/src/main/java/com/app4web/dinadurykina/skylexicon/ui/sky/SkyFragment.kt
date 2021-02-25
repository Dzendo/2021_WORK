package com.app4web.dinadurykina.skylexicon.ui.sky

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app4web.dinadurykina.skylexicon.R
import com.app4web.dinadurykina.skylexicon.databinding.SkyRecyclerListBinding
import com.app4web.dinadurykina.skylexicon.launcher.COUNTSFact
import com.app4web.dinadurykina.skylexicon.launcher.PAEMI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A fragment representing a recycler list of Items.
 * Фрагмент, представляющий список элементов recycler.
 * Использует Paging 3.0 и HILT
 */
@AndroidEntryPoint
class SkyFragment : Fragment() {

    private val skyViewModel: SkyViewModel by viewModels()
    // Создаем skyViewModel без параметров + репо + dao + database и связывается с ними
    // Предлагается передавать сюда состояние списка ????
    // private val args: SkyFragmentArgs by navArgs()
    // Эксперимент используется - взят образец из устаревшей ViewModel для попыток отмены запроса
   // init {
        private var paemiJob: Job? = null
        private val toDoFragmentJob = Job()
        private val uiScope = CoroutineScope(Dispatchers.Main + toDoFragmentJob)
        // Coroutine that will be canceled when the ViewModel is cleared.

       // Timber.i("SkyViewModel created PAEMI= ${paemi.value?.name}")
   // }

    // Стандартный вызов :Fragment из Android
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Сообщает меню ... из SkyActivity что надо будет Добавлять и обрабатывать доп меню три точки для этого фрагмента
        setHasOptionsMenu(true)
        Timber.i("Sky Recycler Fragment onCreate")
    }
    // Стандартный вызов Fragment из Android для надувания макета и др.
    override fun onCreateView(
            inflater: LayoutInflater,               // раздуваетль фрагмента
            container: ViewGroup?,                  // поле фрагмент в котором надо раздуваться
            savedInstanceState: Bundle?             // сохраненные параметры устаревшие из Kitty
    ): View {    // возвращает раздутый и настроенный View для высветки его родительским фрагментом в указанном поле
        Timber.i("Sky Recycler Fragment onCreateView")
        /**
         * Get a reference to the binding object and inflate the fragment views.
         * Получить ссылку на объект привязки и раздуть представления фрагментов.
         * Раздувается databinding из layout\sky_recycler_list.xml по методу для фрагмента
         * Не указан xml который раздувать SkyRecyclerListBinding из этого названия он знает
         */
        val binding = SkyRecyclerListBinding.inflate(inflater, container, false)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        // Чтобы использовать модель представления с привязкой данных, вы должны явно
        // дать объекту привязки ссылку на него.
        // ссылка на skyViewModel засовывается в viewmodel sky_recycler_list.xml
        // теперь sky_recycler_list.xml по этой ссылке может брать из skyViewModel напрямую
        binding.viewmodel = skyViewModel
        binding.lifecycleOwner = viewLifecycleOwner              // владелец цикла жизни этого фрагмента Я

        // Полю recyclerList из sky_recycler_list.xml определяется адаптер как положено для RecyclerView
        // Но вместо обычного адаптера наследуется от спец адаптер из Paging 3.0  PagingDataAdapter<Fact, FactViewHolder>(diffCallback)
        // Где Fact - класс строчки данных из ROOM, FactViewHolder - стандарт , diffCallback - стандартный из RecyclerView но здесь обязателен
        // кроме этого ему передается FactListener { factID -> onFactClicked(factID) }  для CallBack от клика на строчке
        binding.recyclerList.adapter = skyViewModel.adapterPageTable

        // Наблюдатель меняющий буковку фильтра от выбора нижнего меню фрагмента
        skyViewModel.paemi.observe(viewLifecycleOwner) {
            // Попытки отмены ненужного уже запроса при выборе новой буковки внизу
            //toDoFragmentJob.cancel()      // стартует НЕ показывает не отменяет
            paemiJob?.cancel()          // стартует показывает не отменяет
            paemiJob = uiScope.launch {   // стартует показывает не отменяет
           // paemiJob = viewLifecycleOwner.lifecycleScope.launch {   // стартует показывает не отменяет
           // ***** Это основной здесь - Заполнение адаптером строк RecyclerView из потока factsPage: Flow<PagingData<Fact>>***
                skyViewModel.factsPageChangeTable(it)
           // Заполнение адаптером строк RecyclerView осуществляется из потока Paging 3.0 который на фильтре
            }
        }

        // в отличии от активити фрагмент требует вернуть ему ссылку на корень раздутого макета, что бы он его высветил
        return binding.root
    }
    /**
     * Вызывается сразу после {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * вернулся, но до того, как любое сохраненное состояние было восстановлено в представлении.
     * Это дает подклассам возможность инициализировать себя один раз
     * они знают, что их иерархия взглядов была полностью создана. Фрагмент
     * однако иерархия представлений на данном этапе не привязана к своему родителю.
     * @param view представление, возвращаемое {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState если значение не равно null, то этот фрагмент создается заново
     * из предыдущего сохраненного состояния, как указано здесь.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("Sky Recycler Fragment onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        // Он наблюдает когда поступит команда перехода на форму detail (тап в строчку или fab)
        skyViewModel.navigateToFactDetail.observe(viewLifecycleOwner) { factID ->
            // поле LiveData очень часто(почти всегда) null - и это нам не интересно - пропускаем
            factID?.let {
                /**
                 * Получив команду перейти мы зовем NavController это то же самое, что NavHostFragment
                 * Мы ему говорим пошли, навигируй нас в actionTodoFragmentToFactDetailFragment
                 * Он ищет это в mobile_navigation, который он тогда еще считал и помнит
                 * Он находит: <action
                 *            android:id="@+id/action_skyFragment_to_factDetailFragment"
                 *            app:destination="@id/factDetailFragment" />
                 * (подчерки и малые и большие буквы, это правило обращения SkyFragmentDirections -
                 * пакета безопасной навигации из архитектуры)
                 * Указано куда переходить к фрагменту с именем factDetailFragment в файле mobile_navigation
                 * А там <fragment
                 *        android:id="@+id/factDetailFragment"
                 *        android:name="com.app4web.dinadurykina.sky.ui.detail.FactDetailFragment"
                 * соответственно зовет FactDetailFragment.kt из указанного каталога
                 * и говорит ему ты сюда давай размещайся и отдает ему управление
                 * но еще здесь он ему посылает два аргумента factID и paemi (как указано в mobile_navigation)
                 */
                this.findNavController().navigate(
                    SkyFragmentDirections.actionSkyFragmentToFactDetailFragment(factID,skyViewModel.paemi.value?: PAEMI.N))
                skyViewModel.navigateToFactDetailNavigated()
            }
        }
    }

    // Добавляет в меню три точки пункты для этого фрагмента из menu\sky.xml
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sky, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    //  обрабатывает в меню три точки свои добавленные для этого фрагмента пункты меню
    // хорошо бы научиться вызывать из XML прямо ViewModel, но пока не сделали - обещают
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fact_base_creating -> {  // "Добавить пачку"
                skyViewModel.addFactDatabase(COUNTSFact) // Дозаполнить заново базу данных
                Toast.makeText(activity,"База добавлено  $COUNTSFact * 7 = ${COUNTSFact * 7} записей ", Toast.LENGTH_SHORT).show()
            }
            R.id.fact_base_clearing -> {  // "Очисить базу"
                skyViewModel.clear()
                Toast.makeText(activity,"База очищена ", Toast.LENGTH_SHORT).show()
                Timber.i("SkyFactRepository fact_base_clearing База очищена  ")
            }
            R.id.isCancel -> {              // "Отменить поиск"
                skyViewModel.isCancelFlow = true
                Toast.makeText(activity,"Отмена ", Toast.LENGTH_SHORT).show()
            }
            else -> return false
        }
        return true
    }
}