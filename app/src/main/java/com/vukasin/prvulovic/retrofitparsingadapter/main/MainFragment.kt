package com.vukasin.prvulovic.retrofitparsingadapter.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.vukasin.prvulovic.retrofitparsingadapter.R
import com.vukasin.prvulovic.retrofitparsingadapter.login.LoginFragment
import com.vukasin.prvulovic.retrofitparsingadapter.routes.RoutesFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view_pager.adapter = MainPageAdapter(parentFragmentManager)
    }
}

class MainPageAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> RoutesFragment()
            else -> LoginFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Routes"
            else -> "Login"
        }
    }

    override fun getCount(): Int = 2
}