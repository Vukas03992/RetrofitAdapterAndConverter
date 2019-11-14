package com.vukasin.prvulovic.retrofitparsingadapter

import com.vukasin.prvulovic.retrofit_parsing_adapter.Parsable
import com.vukasin.prvulovic.retrofit_parsing_adapter.Parse
import retrofit2.http.GET

interface RouteService {

    @GET("/api/v1/routes")
    fun getRoutes(): Parse<Parsable>
}