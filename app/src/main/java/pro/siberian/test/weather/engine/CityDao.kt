package pro.siberian.test.weather.engine

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.siberian.test.weather.model.City

@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun getCheckedCities(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCheckedCity(vararg city: City)
}
