package com.vukasin.prvulovic.retrofit_parsing_adapter

import retrofit2.Response

class Parse<T> internal constructor(private val response: Response<Parsable>){

    fun doOnSuccess(parse: Parsable.() -> Unit){
        if (response.isSuccessful){
            response.body()?.parse()
        }
    }

    fun doOnError(errorHandler: Response<Parsable>.() -> Unit){
        if(!response.isSuccessful){
            response.errorHandler()
        }
    }
}