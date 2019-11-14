package com.vukasin.prvulovic.retrofitparsingadapter

import com.google.gson.annotations.SerializedName

data class Route(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("origin") val origin: Airport,
    @field:SerializedName("destination") val destination: Airport,
    @field:SerializedName("is_active") val isActive: Boolean,
    @field:SerializedName("is_member") val isMember: Boolean
)

data class Airport(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("code", alternate = ["city_code"]) val code: String,
    @field:SerializedName("type") val type: String?,
    @field:SerializedName("country_name", alternate = ["country"]) val countryName: String?,
    @field:SerializedName("city_name", alternate = ["city"]) val cityName: String? = null,
    @field:SerializedName("name") val name: String
)

data class DataRoute(
    @field:SerializedName("data") val data: List<Route>
)