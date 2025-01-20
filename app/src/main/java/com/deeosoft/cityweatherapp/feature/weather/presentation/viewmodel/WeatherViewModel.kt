package com.deeosoft.cityweatherapp.feature.weather.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.Weather
import com.deeosoft.cityweatherapp.feature.weather.domain.entity.WeatherForecast
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.FavoriteUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SaveFavoriteStateUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SavedCityUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.SearchByCityUseCase
import com.deeosoft.cityweatherapp.feature.weather.domain.usecase.WeatherForecastUseCase
import com.deeosoft.cityweatherapp.infastructure.wrapper.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val searchByCityUseCase: SearchByCityUseCase,
    private val savedCityUseCase: SavedCityUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    private val saveFavoriteStateUseCase: SaveFavoriteStateUseCase,
    private val weatherForecastUseCase: WeatherForecastUseCase
): ViewModel() {
    private var _weatherState = MutableLiveData<UIState<List<Weather?>>>()
    val weatherState: LiveData<UIState<List<Weather?>>> = _weatherState

    private var _savedCityWeatherState = MutableLiveData<UIState<List<Weather?>>>()
    val savedCityWeatherState: LiveData<UIState<List<Weather?>>> = _savedCityWeatherState

    private var _favoriteState = MutableLiveData<UIState<List<Weather?>>>()
    val favoriteState: LiveData<UIState<List<Weather?>>> = _favoriteState

    private var _saveFavoriteState = MutableLiveData<UIState<List<Weather?>>>()
    val saveFavoriteState: LiveData<UIState<List<Weather?>>> = _saveFavoriteState

    private var _weatherForecast = MutableLiveData<UIState<List<WeatherForecast?>>>()
    val weatherForecast: LiveData<UIState<List<WeatherForecast?>>> = _weatherForecast

    fun getWeather(city: String, forceServer: Boolean = false){
        _weatherState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            searchByCityUseCase.execute(city, forceServer).catch {
                _weatherState.postValue(UIState.Error(it.message ?: "Something went wrong"))
            }.collect{
                if(it.isNotEmpty()){
                    _weatherState.postValue(UIState.Success(it))
                }
            }
        }
    }

    fun getSavedCityWeather(){
        _savedCityWeatherState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            savedCityUseCase.execute().catch{
                _savedCityWeatherState.postValue(UIState.Error(it.message ?: "Something went wrong"))
            }.collect{
                if(it.isNotEmpty()){
                    _savedCityWeatherState.postValue(UIState.Success(it))
                }
            }
        }
    }

    fun getFavorite(){
        _favoriteState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            favoriteUseCase.execute().catch{
                _favoriteState.postValue(UIState.Error(it.message ?: "Something went wrong"))
            }.collect{
                if(it.isNotEmpty()){
                    _favoriteState.postValue(UIState.Success(it))
                }
            }
        }
    }

    fun saveFavoriteState(id: Double, state: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            saveFavoriteStateUseCase.execute(id, state).catch{
                _favoriteState.postValue(UIState.Error(it.message ?: "Something went wrong"))
            }.collect{
                _favoriteState.postValue(UIState.Success(it))
            }
        }
    }

    fun getWeatherForecast(city: String, cityId: Double){
        _weatherForecast.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            weatherForecastUseCase.execute(city, cityId).catch{
                _weatherForecast.postValue(UIState.Error(it.message ?: "Something went wrong"))
            }.collect{
                if(it.isNotEmpty()){
                    _weatherForecast.postValue(UIState.Success(it))
                }
            }
        }
    }
}