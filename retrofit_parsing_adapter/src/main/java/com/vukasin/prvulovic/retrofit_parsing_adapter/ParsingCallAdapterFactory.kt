package com.vukasin.prvulovic.retrofit_parsing_adapter

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ParsingCallAdapterFactory private constructor() : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType == Parse::class.java){
            val typeOfTypeArgument = getParameterUpperBound(0, returnType as ParameterizedType)
            val rawTypeOfTypeArgument = getRawType(typeOfTypeArgument)
            if (rawTypeOfTypeArgument == Parsable::class.java){
                return ParsingCallAdapter(returnType)
            }
        }
        return null
    }

    companion object {
        fun create(): ParsingCallAdapterFactory = ParsingCallAdapterFactory()
    }
}
