package pro.siberian.test.weather.engine

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.siberian.test.weather.model.City
import pro.siberian.test.weather.model.CityWeather
import pro.siberian.test.weather.model.Forecast

@Database(version = 1, entities = [City::class, CityWeather::class, Forecast::class])
abstract class LocalDb: RoomDatabase() {
    abstract val forecastDao: ForecastDao
    abstract val weatherDao: WeatherDao
    abstract val cityDao: CityDao

}