package pro.siberian.test.weather.repository

import kotlinx.coroutines.*
import pro.siberian.test.weather.engine.LocalDb
import pro.siberian.test.weather.model.City

class CityRepository(private val db: LocalDb) {
    fun getCheckedCities(): Deferred<List<City>> = CoroutineScope(Dispatchers.IO).async {
        db.cityDao.getCheckedCities()
    }

    fun addCheckedCity(city: City) = CoroutineScope(Dispatchers.IO).launch {
        db.cityDao.addCheckedCity(city)
    }
}