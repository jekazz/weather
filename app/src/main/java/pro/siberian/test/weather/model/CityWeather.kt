package pro.siberian.test.weather.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather")
class CityWeather(): Parcelable {
    @PrimaryKey
    var id: Long = 0

    var name: String = ""

    @SerializedName("dt_txt")
    var date: String = ""

    @Embedded
    lateinit var main: Main
    @Embedded
    lateinit var wind: Wind
    @Embedded
    lateinit var sys: Sys


    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityWeather> {
        override fun createFromParcel(parcel: Parcel): CityWeather {
            return CityWeather(parcel)
        }

        override fun newArray(size: Int): Array<CityWeather?> {
            return arrayOfNulls(size)
        }
    }
}

class Main() : Parcelable {
    var humidity: Int = Int.MIN_VALUE
    var pressure: Float = Float.NaN
    var temp: Float = Float.NaN

    constructor(parcel: Parcel) : this() {
        humidity = parcel.readInt()
        pressure = parcel.readFloat()
        temp = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(humidity)
        parcel.writeFloat(pressure)
        parcel.writeFloat(temp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Main> {
        override fun createFromParcel(parcel: Parcel): Main {
            return Main(parcel)
        }

        override fun newArray(size: Int): Array<Main?> {
            return arrayOfNulls(size)
        }
    }
}

class Wind() : Parcelable {
    var deg: Float = Float.NaN
    var speed: Float = Float.NaN

    constructor(parcel: Parcel) : this() {
        deg = parcel.readFloat()
        speed = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(deg)
        parcel.writeFloat(speed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Wind> {
        override fun createFromParcel(parcel: Parcel): Wind {
            return Wind(parcel)
        }

        override fun newArray(size: Int): Array<Wind?> {
            return arrayOfNulls(size)
        }
    }

}

class Sys() : Parcelable {
    var country: String = ""

    constructor(parcel: Parcel) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sys> {
        override fun createFromParcel(parcel: Parcel): Sys {
            return Sys(parcel)
        }

        override fun newArray(size: Int): Array<Sys?> {
            return arrayOfNulls(size)
        }
    }
}