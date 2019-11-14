package com.vukasin.prvulovic.retrofit_parsing_adapter

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonIOException
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import java.lang.reflect.Type

class Parsable internal constructor(
    private val responseBody: ResponseBody,
    private val gson: Gson
){

    fun <T> parseObject(type: Type, makeJsonElement: JsonElement.() -> JsonElement): T {

        val jsonReader = gson.newJsonReader(responseBody.charStream())
        val json = JsonParser.parseReader(jsonReader)
        if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
            throw JsonIOException("JSON document was not fully consumed.")
        }
        val jsonElement = json.makeJsonElement()

        return gson.fromJson(jsonElement.asJsonObject, type)
    }

    fun <T> parseArray(type: Type, makeJsonElement: JsonElement.() -> JsonElement): List<T> {

        val jsonReader = gson.newJsonReader(responseBody.charStream())
        val json = JsonParser.parseReader(jsonReader)
        if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
            throw JsonIOException("JSON document was not fully consumed.")
        }
        val jsonElement = json.makeJsonElement()

        println(jsonElement.toString())

        val list = mutableListOf<T>()

        println(list)

        jsonElement.asJsonArray.forEach {
            list.add(gson.fromJson(it.asJsonObject, type))
        }

        println(list)

        return list
    }
}