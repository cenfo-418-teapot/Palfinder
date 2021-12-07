package com.example.palfinder.components

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.palfinder.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_input_tag.*
import kotlinx.android.synthetic.main.fragment_input_tag.view.*
import kotlin.streams.toList


interface OnIdListChange {
    fun onIdListChange(list: List<String>)
}

class TagInputFragment : Fragment() {

    private lateinit var _idListListener: OnIdListChange
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _idListListener = context as OnIdListChange
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_input_tag, container, false)
        val chipGroup = view.cgSelectedTags
        val amount = view.tvAmount
        setupToggleCounterVisibilityListener(amount, view.llCounter)
        setupCreateTagInputListener(view.tilTag, view.etTag, chipGroup, amount)
        return view
    }

    private fun pushTagList() {
        val tagsList = cgSelectedTags.checkedChipIds.stream().map { id -> cgSelectedTags.findViewById<Chip>(id).text.toString() }.toList()
        _idListListener.onIdListChange(tagsList)
    }

    private fun setupCreateTagInputListener(
        textInputLayout: TextInputLayout,
        textView: TextView,
        chipGroup: ChipGroup,
        amount: TextView,
    ) {
        textView.setOnEditorActionListener(TextView.OnEditorActionListener { tv, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val addResult = addSelectedChip(tv.text.toString(), chipGroup, amount)
                textInputLayout.error = addResult
                tv.text = ""
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun setupToggleCounterVisibilityListener(counterTextView: TextView, viewToHide: View) {
        counterTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var visibility = View.GONE
                if (p0.toString() != "0") {
                    visibility = View.VISIBLE
                }
                viewToHide.visibility = visibility
                Log.i(TAG, "Label visibility = $visibility")
            }
        })
    }

    private fun addSelectedChip(title: String, chipGroup: ChipGroup, amount: TextView): String? {
        val name = title.lowercase().trim()
        val exists =
            chipGroup.checkedChipIds.any { id -> chipGroup.findViewById<Chip>(id).text == name }
        if (name.isBlank()) return "Empty tag names are not allowed"
        else if (!exists) {
            val chip = layoutInflater.inflate(R.layout.selected_tag, chipGroup, false) as Chip
            chip.text = name
            chip.setOnClickListener {
                chipGroup.removeView(it)
                amount.text = chipGroup.checkedChipIds.size.toString()
                pushTagList()
            }
            chipGroup.addView(chip)
            amount.text = chipGroup.checkedChipIds.size.toString()
            pushTagList()
            return null
        }
        return "$name is already selected!"
    }

    companion object {
        private const val TAG = "CreateTagFragment"
    }
}