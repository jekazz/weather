package pro.siberian.test.weather.ui.addcity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.androidannotations.annotations.App
import org.androidannotations.annotations.EActivity
import pro.siberian.test.weather.R
import pro.siberian.test.weather.WeatherApp
import pro.siberian.test.weather.databinding.ActivityAddCityBinding
import pro.siberian.test.weather.engine.ViewModelFactory
import pro.siberian.test.weather.model.CityWeather

const val ADD_CITY_REQUEST_CODE = 0

@SuppressLint("Registered")
@EActivity
open class AddCityActivity : AppCompatActivity() {

    @App
    lateinit var app: WeatherApp

    private lateinit var ui: ActivityAddCityBinding
    private lateinit var vm: AddCityActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_add_city)
        vm = ViewModelProviders.of(this, ViewModelFactory(app)).get(AddCityActivityViewModel::class.java)

        observeData()
        bindUI()
    }

    private fun observeData() {
        vm.weathers.observe(this, Observer { showList(it) })
    }

    private fun bindUI() {
        ui.cityName.addTextChangedListener(watcher)
    }

    private fun showList(weathers: List<CityWeather>) {
        val adapter = CityAdapter(this, weathers!!)
        adapter.itemClickListener = object : CityAdapter.ItemClickListener {
            override fun onItemClick(item: CityWeather) {
                val intent = Intent()
                intent.putExtra("city_name", item.name)
                intent.putExtra("city_id", item.id)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        ui.list.adapter = adapter
        ui.list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private val watcher = object : TextWatcher {
        private var searchFor = ""

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchText = s.toString().trim()
            if (searchText == searchFor)
                return
            searchFor = searchText

            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                if (searchText != searchFor)
                    return@launch

                vm.searchCity(searchFor)
            }

        }

        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    }
}
