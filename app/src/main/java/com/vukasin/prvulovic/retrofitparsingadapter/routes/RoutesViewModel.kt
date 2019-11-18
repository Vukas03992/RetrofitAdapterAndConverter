package com.vukasin.prvulovic.retrofitparsingadapter.routes

import android.util.Log.e
import androidx.lifecycle.*
import com.vukasin.prvulovic.retrofitparsingadapter.Route
import com.vukasin.prvulovic.retrofitparsingadapter.RouteService
import com.vukasin.prvulovic.retrofitparsingadapter.main.MainActivity
import kotlinx.coroutines.Dispatchers

class RoutesViewModel : ViewModel() {

    val requestRoutes = MutableLiveData<Boolean>()

    val routeLiveData: LiveData<List<Route>> = Transformations.switchMap(requestRoutes){
        liveData(Dispatchers.IO){
            val routes = getRoutes()
            e("TAG","djskdjk")
            emit(routes)
        }
    }

    private fun getRoutes(): List<Route> {
        val service = MainActivity.retrofit.create(RouteService::class.java)
        var routes = emptyList<Route>()
        service.getRoutes().apply {
            doOnSuccess {
                routes = parseArray(Route::class.java) {
                    asJsonObject["data"]
                }
            }
        }
        e("TAG", routes.toString())
        return routes
    }

}