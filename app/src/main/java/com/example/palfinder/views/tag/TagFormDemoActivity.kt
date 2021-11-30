package com.example.palfinder.views.tag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.palfinder.R
import com.example.palfinder.components.OnIdListChange
import kotlinx.android.synthetic.main.activity_tag_form_demo.*

class TagFormDemoActivity : AppCompatActivity(), OnIdListChange {
    private var tagsList = MutableLiveData<List<String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_form_demo)
        tagsList.observe(this, {
            val tags = tagsList.value!!.toString()
            tvTagList.text = tags
        })
    }

    override fun onIdListChange(ids: List<String>) {
        tagsList.postValue(ids)
    }
}