package com.vukasin.prvulovic.retrofitparsingadapter.routes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vukasin.prvulovic.retrofitparsingadapter.R
import com.vukasin.prvulovic.retrofitparsingadapter.Route
import kotlinx.android.synthetic.main.fragment_routes.*
import kotlinx.android.synthetic.main.item_route.view.*

class RoutesFragment : Fragment(R.layout.fragment_routes) {

    private val viewModel: RoutesViewModel by viewModels { ViewModelProvider.NewInstanceFactory() }
    private val behavior by lazy {
        BottomSheetBehavior.from(bottom_sheet)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list_routes.layoutManager = LinearLayoutManager(requireContext())
        val adapter = RouteAdapter()
        list_routes.adapter = adapter
        viewModel.routeLiveData.observe(viewLifecycleOwner, Observer {
            progress.visibility = View.GONE
            adapter.data = it
            text_instructions.visibility = View.GONE
        })
        button_routes_execute.setOnClickListener {
            progress.visibility = View.VISIBLE
            viewModel.requestRoutes.value = true
        }
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        button_routes_open.setOnClickListener {
            button_routes_open.visibility = View.GONE
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) button_routes_open.visibility = View.VISIBLE
            }
        })
    }
}


class RouteAdapter : RecyclerView.Adapter<RouteHolder>() {

    var data: List<Route> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
        return RouteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_route, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RouteHolder, position: Int) {
        holder.bindRoute(data[position])
    }

}

class RouteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindRoute(route: Route) {
        itemView.text_title.text = "${route.origin.code} - ${route.destination.code}"
        itemView.text_content.text = "${route.origin} to ${route.destination}"
    }
}