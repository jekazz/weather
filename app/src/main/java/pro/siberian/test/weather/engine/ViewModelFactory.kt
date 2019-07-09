package pro.siberian.test.weather.engine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.siberian.test.weather.WeatherApp

class ViewModelFactory(private val app: WeatherApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getConstructor(WeatherApp::class.java)
            constructor.isAccessible = true
            return constructor.newInstance(app)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return null!!
    }
}

