package pro.siberian.test.weather.engine

import kotlinx.coroutines.Deferred
import pro.siberian.test.weather.model.CityWeather
import pro.siberian.test.weather.model.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("/data/2.5/weather")
    fun getWeather(@Query("id") id: Long): Deferred<CityWeather>

    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") name: String): Deferred<CityWeather>

    @GET("data/2.5/forecast")
    fun getForecast(@Query("id") cityId: Long): Deferred<Forecast>
}