package pro.siberian.test.weather.engine

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.siberian.test.weather.model.CityWeather

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE id=:cityId")
    fun getWeather(cityId: Long): CityWeather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setWeather(weathers: CityWeather)
}
