
package com.vukasin.prvulovic.retrofit_parsing_adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ParsingCallAdapter internal constructor(val responseType: Type): CallAdapter<Parsable, Parse<Parsable>>{

    override fun adapt(call: Call<Parsable>): Parse<Parsable> {
        val response = call.execute()
        return Parse(response)
    }

    override fun responseType(): Type {
        return responseType
    }

}
