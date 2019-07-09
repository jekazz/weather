package pro.siberian.test.weather.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.androidannotations.annotations.*
import pro.siberian.test.weather.R
import pro.siberian.test.weather.WeatherApp
import pro.siberian.test.weather.databinding.ActivityMainBinding
import pro.siberian.test.weather.engine.ViewModelFactory
import pro.siberian.test.weather.model.City
import pro.siberian.test.weather.model.CityWeather
import pro.siberian.test.weather.ui.addcity.ADD_CITY_REQUEST_CODE
import pro.siberian.test.weather.ui.addcity.AddCityActivity_
import pro.siberian.test.weather.ui.forecast.ForecastActivity_

@SuppressLint("Registered")
@EActivity
open class MainActivity : AppCompatActivity() {
    private lateinit var ui: ActivityMainBinding
    private lateinit var vm: MainActivityViewModel

    @App
    lateinit var app: WeatherApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = ViewModelProviders.of(this,ViewModelFactory(app)).get(MainActivityViewModel::class.java)

        observeData()

        if (vm.checkedCities.value == null) {
            vm.requestCheckedCities()
        }
    }

    private fun observeData() {
        vm.checkedCities.observe(this, Observer { vm.requestCurrentWeather(it) })
        vm.briefForecast.observe(this, Observer { populateList(it) })
        vm.error.observe(this, Observer { showError(it!!) })
    }

    private fun populateList(weathers: List<CityWeather>) {
        val adapter = WeatherBriefAdapter(this, weathers)
        ui.briefForecast.adapter = adapter

        adapter.itemClickListener = object : WeatherBriefAdapter.ItemClickListener {
            override fun onItemClick(item: CityWeather) {
                ForecastActivity_.intent(this@MainActivity).cityId(item.id).start()
            }
        }
        ui.briefForecast.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun showError(error: Throwable) {
        Snackbar.make(ui.root, R.string.query_error, Snackbar.LENGTH_LONG).show()
        error.printStackTrace()
    }

    @Click(R.id.addCity)
    fun addCity() {
        AddCityActivity_.intent(this).startForResult(ADD_CITY_REQUEST_CODE)
    }

    @OnActivityResult(ADD_CITY_REQUEST_CODE)
    fun addCityAndRequestWeather(resultCode: Int, @OnActivityResult.Extra("city_id") id: Long?,
                                 @OnActivityResult.Extra("city_name") name: String?) {
        if (resultCode == Activity.RESULT_OK) {
            vm.addCity(City(id!!, name!!))
        }
    }
}
