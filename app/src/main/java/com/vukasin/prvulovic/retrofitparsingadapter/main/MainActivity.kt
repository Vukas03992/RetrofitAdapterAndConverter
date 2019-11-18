package com.vukasin.prvulovic.retrofitparsingadapter.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.vukasin.prvulovic.retrofit_parsing_adapter.ParsableConverterFactory
import com.vukasin.prvulovic.retrofit_parsing_adapter.ParsingCallAdapterFactory
import com.vukasin.prvulovic.retrofitparsingadapter.DataRoute
import com.vukasin.prvulovic.retrofitparsingadapter.R
import com.vukasin.prvulovic.retrofitparsingadapter.Route
import com.vukasin.prvulovic.retrofitparsingadapter.RouteService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = ""
    }

    companion object{
        val gson = Gson()

        val retrofit = Retrofit.Builder()
        .baseUrl("https://staging.reijets.bstorm.app")
        .addConverterFactory(ParsableConverterFactory.create(gson))
        .addCallAdapterFactory(ParsingCallAdapterFactory.create())
        .build()
    }
}
