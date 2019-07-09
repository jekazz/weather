package pro.siberian.test.weather.engine

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.siberian.test.weather.model.CityWeather
import pro.siberian.test.weather.model.Forecast

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast WHERE id=:cityId")
    fun getForecast(cityId: Long): Forecast

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setForecast(forecast: Forecast)
}
