package pro.siberian.test.weather.ui.forecast

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import pro.siberian.test.weather.WeatherApp
import pro.siberian.test.weather.model.Forecast
import pro.siberian.test.weather.ui.BaseViewModel

class ForecastActivityModelView(app: WeatherApp) : BaseViewModel() {
    private var forecastRequest: Deferred<Forecast>? = null

    val forecastRepository = app.repositories.forecastRepository

    val forecast = MutableLiveData<Forecast?>()
    val error = MutableLiveData<Throwable>()

    fun requestForecast(cityId: Long) = launch {
        if (forecastRequest == null) {
            try {
                forecastRequest = forecastRepository.getForecast(cityId)
                forecastRequest!!.await().also { forecast.postValue(it) }
            } catch (e: Throwable) {
                error.postValue(e)
            } finally {
                forecastRequest = null
            }
        }
    }

}
