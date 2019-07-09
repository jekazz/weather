package pro.siberian.test.weather.di

import dagger.Component
import pro.siberian.test.weather.repository.CityRepository
import pro.siberian.test.weather.repository.ForecastRepository

@Component(modules = [CityRepositoriesModule::class, ForecastRepositoryModule::class])
interface RepositoryComponent {
    val cityRepository: CityRepository
    val forecastRepository: ForecastRepository
}