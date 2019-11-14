package com.vukasin.prvulovic.retrofitparsingadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.vukasin.prvulovic.retrofit_parsing_adapter.ParsableConverterFactory
import com.vukasin.prvulovic.retrofit_parsing_adapter.ParsingCallAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = Gson()

        retrofit = Retrofit.Builder()
            .baseUrl("https://staging.reijets.bstorm.app")
            .addConverterFactory(ParsableConverterFactory.create(gson))
            .addCallAdapterFactory(ParsingCallAdapterFactory.create())
            .build()

        val service = retrofit.create(RouteService::class.java)

        lifecycleScope.launch(Dispatchers.Default) {
            var dataRoute: DataRoute? = null
            var routes = emptyList<Route>()

            ///////////////////////////////////////////////////////////////////////

            service.getRoutes().apply {
                doOnSuccess {
                    dataRoute = parseObject(DataRoute::class.java) {
                        this
                    }
                }
            }

            ///////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////////

            service.getRoutes().apply {
                doOnSuccess {
                    routes = parseArray(Route::class.java) {
                        asJsonObject["data"]
                    }
                }
            }

            ///////////////////////////////////////////////////////////////////////

            val routesText = buildString {
                dataRoute?.data?.forEach {
                    append(it)
                    append("\n\n\n")
                }
                routes.forEach {
                    append(it)
                    append("\n\n\n")
                }
            }

            e("TAG", routesText)

            launch(Dispatchers.Main) {
                routes_text.text = routesText
            }
        }

    }
}
