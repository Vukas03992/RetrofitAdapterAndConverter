package com.vukasin.prvulovic.retrofit_parsing_adapter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import com.google.gson.TypeAdapter
import okhttp3.MediaType
import okio.Buffer
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.charset.Charset


class ParsableConverterFactory private constructor(val gson: Gson): Converter.Factory(){

    companion object{
        fun create(gson: Gson): ParsableConverterFactory = ParsableConverterFactory(gson)
    }

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        if(type is ParameterizedType){
            val typeOfTypeArgument = getParameterUpperBound(0,type)
            if (typeOfTypeArgument == Parsable::class.java){
                return ResponseCustomParsingConverter<Parsable>(gson)
            }
        }
        return null
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson,adapter)
    }
}

@Suppress("UNCHECKED_CAST")
class ResponseCustomParsingConverter<T> internal constructor(val gson: Gson): Converter<ResponseBody, T>{
    override fun convert(value: ResponseBody): T? {
        return Parsable(value, gson) as T
    }
}

internal class GsonRequestBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<T, RequestBody> {
    @Throws(IOException::class)
    override fun convert(value: T): RequestBody? {
        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }

    companion object {
        private val MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8")
        private val UTF_8 = Charset.forName("UTF-8")
    }
}

