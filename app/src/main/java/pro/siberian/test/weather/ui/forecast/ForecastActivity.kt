package pro.siberian.test.weather.ui.forecast

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pro.siberian.test.weather.R

import org.androidannotations.annotations.App
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra
import org.androidannotations.annotations.InstanceState
import pro.siberian.test.weather.WeatherApp
import pro.siberian.test.weather.databinding.ActivityForecastBinding
import pro.siberian.test.weather.engine.ViewModelFactory
import pro.siberian.test.weather.model.Forecast

@SuppressLint("Registered")
@EActivity
open class ForecastActivity : AppCompatActivity() {
    @App
    protected lateinit var app: WeatherApp

    @Extra
    @JvmField
    var cityId: Long = Long.MIN_VALUE

    private lateinit var ui: ActivityForecastBinding
    private lateinit var vm: ForecastActivityModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_forecast)
        vm = ViewModelProviders.of(this, ViewModelFactory(app)).get(ForecastActivityModelView::class.java)

        observeData()
        bindUI()

        if (vm.forecast.value == null) {
            vm.requestForecast(cityId)
        }
    }

    private fun observeData() {
        vm.forecast.observe(this, Observer { ui.swipe.isRefreshing = false; it?.let { showForecast(it) } })
        vm.error.observe(this, Observer { ui.swipe.isRefreshing = false; showError(it!!) })
    }

    private fun bindUI() {
        ui.swipe.setOnRefreshListener { vm.requestForecast(cityId) }
    }

    private fun showForecast(forecast: Forecast) {
        val adapter = ForecastAdapter(this, forecast.dayWeathers)
        ui.list.adapter = adapter
        ui.list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        ui.city = forecast.city
    }

    private fun showError(error: Throwable) {
        Snackbar.make(ui.root, R.string.query_error, Snackbar.LENGTH_LONG).show()
        error.printStackTrace()
    }
}
