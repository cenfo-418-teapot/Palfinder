package com.example.palfinder.views.user.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.palfinder.R
import com.example.palfinder.backend.services.InitialSetupData
import com.example.palfinder.views.HomeActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_initial_setup_confirmation.view.*

class InitialSetupConfirmationFragment : Fragment() {
//    private val initialSetupViewModel: InitialSetupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_setup_confirmation, container, false)
        InitialSetupData.tagsList.observe(viewLifecycleOwner, { tagNamesList ->
            view.cgSelectedTags.removeAllViews()
            if (tagNamesList.isNotEmpty()) {
                view.cgSelectedTags.visibility = View.VISIBLE
                view.emptyTagSelection.visibility = View.GONE
                tagNamesList.forEach {
                    val chip = addSelectedChip(it, view.cgSelectedTags)
                    chip?.setOnCloseIconClickListener { chp ->
                        InitialSetupData.removeTag(chip.text.toString())
                        view.cgSelectedTags.removeView(chp)
                    }
                }
            }
        })
        InitialSetupData.groupsList.observe(viewLifecycleOwner, { groupNamesList ->
            toggleGroupsContentUI(view, groupNamesList.isNotEmpty())
            groupNamesList.forEach {
                val chip = addSelectedChip(it.name, view.cgSelectedGroups)
                chip?.setOnCloseIconClickListener { chp ->
                    InitialSetupData.removeGroup(chip.text.toString())
                    view.cgSelectedGroups.removeView(chp)
                }
            }
        })
        requireActivity().findViewById<Button>(R.id.btnSave).setOnClickListener {
            startActivity(Intent(activity, HomeActivity::class.java))
        }

        return view
    }

    private fun toggleGroupsContentUI(view: View, visible: Boolean) {
        val visibility = if(visible) View.VISIBLE else View.GONE
        val emptyGroupsMsg = if(visible) View.GONE else View.VISIBLE
        view.cgSelectedGroups.visibility = visibility
        view.emptyGroupSelection.visibility = emptyGroupsMsg
    }

    private fun addSelectedChip(name: String, chipGroup: ChipGroup): Chip? {
        val exists =
            chipGroup.checkedChipIds.any { id -> chipGroup.findViewById<Chip>(id).text == name }
        if (!exists) {
            val chip = layoutInflater.inflate(R.layout.selected_tag, chipGroup, false) as Chip
            chip.text = name
            chipGroup.addView(chip)
            Log.i(TAG, "Created chip $name")
            return chip
        }
        return null
    }

    companion object {
        private const val TAG = "InitialSetupConfirmationFragment"
    }
}