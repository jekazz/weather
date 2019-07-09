package pro.siberian.test.weather.di

import dagger.Module
import dagger.Provides
import pro.siberian.test.weather.engine.LocalDb
import pro.siberian.test.weather.repository.CityRepository

@Module(includes = [LocalDbModule::class, OpenWeatherServiceModule::class])
class CityRepositoriesModule {
    @Provides
    fun cityRepository(db: LocalDb): CityRepository {
        return CityRepository(db)
    }
}
