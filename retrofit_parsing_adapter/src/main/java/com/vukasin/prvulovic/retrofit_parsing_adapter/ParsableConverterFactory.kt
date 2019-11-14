package com.vukasin.prvulovic.retrofit_parsing_adapter

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ParsableConverterFactory private constructor(val gson: Gson): Converter.Factory(){

    companion object{
        fun create(gson: Gson): ParsableConverterFactory = ParsableConverterFactory(gson)
    }

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        return ResponseCustomParsingConverter<Parsable>(gson)
    }
}

class ResponseCustomParsingConverter<T> internal constructor(val gson: Gson): Converter<ResponseBody, T>{

    override fun convert(value: ResponseBody): T? {
        return Parsable(value, gson) as T
    }

}