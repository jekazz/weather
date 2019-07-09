package pro.siberian.test.weather.engine

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import pro.siberian.test.weather.R

open class OpenWeatherKeyRequestInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val newUrl = request.url().newBuilder()
            .addQueryParameter("APPID", context.getString(R.string.open_weather_api_key))
            .addQueryParameter("units", "metric")
            .build()
        request = request.newBuilder().url(newUrl).build()

        val response = chain.proceed(request)

        return response
    }
}
