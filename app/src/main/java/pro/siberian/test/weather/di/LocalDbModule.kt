package pro.siberian.test.weather.di

import dagger.Module
import dagger.Provides
import pro.siberian.test.weather.engine.LocalDb

@Module
class LocalDbModule(@get:Provides val db: LocalDb)