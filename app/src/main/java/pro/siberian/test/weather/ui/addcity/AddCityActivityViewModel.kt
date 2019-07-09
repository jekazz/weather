package pro.siberian.test.weather.ui.addcity

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import pro.siberian.test.weather.WeatherApp
import pro.siberian.test.weather.model.CityWeather
import pro.siberian.test.weather.ui.BaseViewModel

class AddCityActivityViewModel(app: WeatherApp): BaseViewModel() {

    val weathers = MutableLiveData<List<CityWeather>>()

    private var request: Deferred<CityWeather>? = null
    private val weatherRepository = app.repositories.forecastRepository

    fun searchCity(cityName: String) = launch {
        try {
            request?.cancel()
            request = weatherRepository.getWeather(cityName)
            weathers.postValue(arrayListOf(request!!.await()))
        } catch (e: Throwable) {
            weathers.postValue(emptyList())
        } finally {
            request = null
        }
    }
}
