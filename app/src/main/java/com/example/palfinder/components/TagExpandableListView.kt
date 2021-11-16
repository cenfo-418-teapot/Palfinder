package com.example.palfinder.components

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.palfinder.R

class TagExpandableListAdapter internal constructor(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, MutableList<String>>
) : BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
    }
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var cnvrtView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (cnvrtView == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cnvrtView = layoutInflater.inflate(R.layout.expandable_row_item, null)
        }
        cnvrtView!!.findViewById<TextView>(R.id.tvTitle).text = expandedListText
        cnvrtView!!.findViewById<ImageView>(R.id.btnRemove).setOnClickListener {
            dataList[getGroup(listPosition)]?.removeAt(expandedListPosition)
            Toast.makeText(context, "Removed Child $expandedListText", Toast.LENGTH_SHORT).show()
            this.notifyDataSetChanged()
        }
        return cnvrtView
    }
    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }
    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }
    override fun getGroupCount(): Int {
        return this.titleList.size
    }
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var cnvrtView = convertView
        val listTitle = getGroup(listPosition) as String
        val groupViewId = R.layout.expandable_row
        if (cnvrtView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            cnvrtView = layoutInflater.inflate(groupViewId, null)
        }
        val tvTitle = cnvrtView!!.findViewById<TextView>(R.id.tvTitle)
        val tvAmount = cnvrtView!!.findViewById<TextView>(R.id.tvAmount)
        tvTitle.setTypeface(null, Typeface.BOLD)
        val itemAmount = getChildrenCount(listPosition)
        tvTitle.text = listTitle
        tvAmount.text = itemAmount.toString()
        return cnvrtView
    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}
