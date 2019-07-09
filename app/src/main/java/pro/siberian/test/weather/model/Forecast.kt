package pro.siberian.test.weather.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import pro.siberian.test.weather.engine.JSonSerializer

@Entity(tableName = "forecast", primaryKeys = ["id"])
class Forecast() : Parcelable {
    @Embedded
    var city: City = City(Long.MIN_VALUE, "")

    @ColumnInfo(name = "forecast")
    @TypeConverters(JSonSerializer::class)
    @SerializedName("list")
    var dayWeathers = ArrayList<CityWeather>()

    constructor(parcel: Parcel) : this() {
        city = parcel.readParcelable(City::class.java.classLoader)
        dayWeathers = parcel.createTypedArrayList(CityWeather)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(city, flags)
        parcel.writeTypedList(dayWeathers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Forecast> {
        override fun createFromParcel(parcel: Parcel): Forecast {
            return Forecast(parcel)
        }

        override fun newArray(size: Int): Array<Forecast?> {
            return arrayOfNulls(size)
        }
    }
}
