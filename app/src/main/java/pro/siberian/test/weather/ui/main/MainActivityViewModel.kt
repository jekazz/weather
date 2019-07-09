package pro.siberian.test.weather.ui.main

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import pro.siberian.test.weather.WeatherApp
import pro.siberian.test.weather.model.City
import pro.siberian.test.weather.model.CityWeather
import pro.siberian.test.weather.ui.BaseViewModel

open class MainActivityViewModel(app: WeatherApp) : BaseViewModel() {
    val checkedCities = MutableLiveData<List<City>>()
    val briefForecast = MutableLiveData<List<CityWeather>>()
    val error = MutableLiveData<Throwable>()

    private var queryCities: Deferred<List<City>>? = null

    val cityRepository = app.repositories.cityRepository
    val forecastRepository = app.repositories.forecastRepository

    fun requestCheckedCities() = launch {
        if (queryCities == null) {
            try {
                queryCities = cityRepository.getCheckedCities()
                checkedCities.postValue(queryCities!!.await())
            } catch (e: Throwable) {
                error.postValue(e)
            } finally {
                queryCities = null
            }
        }
    }

    fun requestCurrentWeather(cities: Collection<City>) = launch {
        try {
            val deferreds = cities.map { forecastRepository.getWeather(it.id) }
            val forecasts = deferreds.awaitAll()
            briefForecast.postValue(forecasts.filterNotNull())
        } catch (e: Throwable) {
            error.postValue(e)
        } finally {
        }
    }

    fun addCity(city: City) = launch {
        try {
            cityRepository.addCheckedCity(city).join()

            queryCities?.cancel()
            queryCities = cityRepository.getCheckedCities()
            checkedCities.postValue(queryCities!!.await())
        } catch (e: Throwable) {
            error.postValue(e)
        } finally {
            queryCities = null
        }
    }
}