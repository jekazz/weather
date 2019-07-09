package pro.siberian.test.weather.engine

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pro.siberian.test.weather.model.CityWeather

class JSonSerializer {
    @TypeConverter
    fun listToJson(list: ArrayList<CityWeather>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToList(json: String): ArrayList<CityWeather> {
        val type = object : TypeToken<ArrayList<CityWeather>>() {}.type
        return Gson().fromJson(json, type)
    }
}
