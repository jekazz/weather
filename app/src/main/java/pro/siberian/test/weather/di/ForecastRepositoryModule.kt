package pro.siberian.test.weather.di

import dagger.Module
import dagger.Provides
import pro.siberian.test.weather.engine.LocalDb
import pro.siberian.test.weather.engine.OpenWeatherService
import pro.siberian.test.weather.repository.ForecastRepository

@Module(includes = [LocalDbModule::class, OpenWeatherServiceModule::class])
class ForecastRepositoryModule {
    @Provides
    fun forecastRepository(db: LocalDb, web: OpenWeatherService): ForecastRepository {
        return ForecastRepository(db, web)
    }
}
