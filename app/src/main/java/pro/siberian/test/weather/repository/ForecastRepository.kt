package pro.siberian.test.weather.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import pro.siberian.test.weather.engine.LocalDb
import pro.siberian.test.weather.engine.OpenWeatherService

open class ForecastRepository(private val db: LocalDb, private val web: OpenWeatherService) {

    open suspend fun getWeather(cityId: Long) = CoroutineScope(Dispatchers.IO).async {
        try {
            val weather = web.getWeather(cityId).await()
            db.weatherDao.setWeather(weather)
            weather
        } catch (e: Throwable) {
            db.weatherDao.getWeather(cityId)
        }
    }

    open suspend fun getWeather(cityName: String) = withContext(Dispatchers.IO) {
        web.getWeather(cityName)
    }

    open suspend fun getForecast(cityId: Long) = CoroutineScope(Dispatchers.IO).async  {
        try {
            val forecast = web.getForecast(cityId).await()
            db.forecastDao.setForecast(forecast)
            forecast
        } catch (e: Throwable) {
            db.forecastDao.getForecast(cityId)
        }
    }
}
