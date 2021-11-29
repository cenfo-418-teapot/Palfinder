package com.example.palfinder.views.user.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.palfinder.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_initial_setup_confirmation.view.*

class InitialSetupConfirmationFragment : Fragment() {
    private val initialSetupViewModel: InitialSetupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_setup_confirmation, container, false)
        initialSetupViewModel.tagsList.observe(viewLifecycleOwner, { tagNamesList ->
            view.cgSelectedTags.removeAllViews()
            if (tagNamesList.isNotEmpty()){
                view.cgSelectedTags.visibility = View.VISIBLE
                view.emptyTagSelection.visibility = View.GONE
                tagNamesList.forEach {
                    addSelectedChip(it, view.cgSelectedTags)
                }
            }
        })
        requireActivity().findViewById<Button>(R.id.btnSave).setOnClickListener {
        }

        return view
    }

    private fun addSelectedChip(name: String, chipGroup: ChipGroup): Boolean {
        val exists =
            chipGroup.checkedChipIds.any { id -> chipGroup.findViewById<Chip>(id).text == name }
        if (!exists) {
            val chip = layoutInflater.inflate(R.layout.selected_tag, chipGroup, false) as Chip
            chip.text = name
            chip.setOnClickListener {
                chipGroup.removeView(it)
            }
            chipGroup.addView(chip)
            Log.i(TAG, "Created chip $name")
            return true
        }
        return false
    }

    companion object {
        private const val TAG = "InitialSetupConfirmationFragment"
    }
}