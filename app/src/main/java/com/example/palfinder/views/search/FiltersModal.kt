package com.example.palfinder.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.palfinder.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FiltersModal : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_bottom_filters_sheet, container, false)
}
