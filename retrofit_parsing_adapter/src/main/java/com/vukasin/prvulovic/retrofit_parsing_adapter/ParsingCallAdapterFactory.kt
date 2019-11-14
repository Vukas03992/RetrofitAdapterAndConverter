
package com.vukasin.prvulovic.retrofit_parsing_adapter

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ParsingCallAdapterFactory private constructor(): CallAdapter.Factory(){

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        return ParsingCallAdapter(returnType)
    }

    companion object{
        fun create(): ParsingCallAdapterFactory = ParsingCallAdapterFactory()
    }
}
