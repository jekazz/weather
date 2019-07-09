package pro.siberian.test.weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pro.siberian.test.weather.engine.*
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [ContextModule::class])
class OpenWeatherServiceModule {
    @Provides
    fun weatherService(okHttpClient: OkHttpClient, callFactory: CallAdapter.Factory): OpenWeatherService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callFactory)
            .build()
            .create(OpenWeatherService::class.java)
    }

    @Provides
    fun getCallFactory(): CallAdapter.Factory {
        return CoroutineCallAdapterFactory()
    }

    @Provides
    fun httpOkClient(loggingInterceptor: HttpLoggingInterceptor,
                     requestInterceptor: OpenWeatherKeyRequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .followRedirects(false)
            .followSslRedirects(false)
            .build()
    }

    @Provides
    fun requestInterceptor(context: Context) = OpenWeatherKeyRequestInterceptor(context)

    @Provides
    fun httpLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    companion object {
        private val WEATHER_BASE_URL = "http://api.openweathermap.org"
    }
}
