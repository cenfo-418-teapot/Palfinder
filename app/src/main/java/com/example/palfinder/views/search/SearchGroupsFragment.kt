package com.example.palfinder.views.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.palfinder.R
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchGroupsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_search, container, false)
        SearchData.searchTerm.observe(viewLifecycleOwner, {
            // update recycler view
        })
        return view
    }
}
