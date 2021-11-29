package com.example.palfinder.views.user.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Tag
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.components.OnIdListChange
import com.example.palfinder.views.HomeActivity
import kotlinx.android.synthetic.main.activity_initial_account_setup.*

class InitialAccountSetup : AppCompatActivity(), OnIdListChange {
    private var _tagsList = MutableLiveData<List<String>>()
    private val _existingTagsList = mutableListOf<Pair<String, String>>()
    private var groupsList = MutableLiveData<List<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_account_setup)
        val uid = intent.getStringExtra("uid")
        Amplify.API.query(
            ModelQuery.get(User::class.java, uid!!),
            {
                UserData.setCurrentUser(it.data)
            },
            { Log.e(TAG, "Query failed", it) }
        )
        setupViewPager()
        btnOmit.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
        this._tagsList.observe(this, {
            it.forEach { tagName ->
                Amplify.API.query(ModelQuery.list(Tag::class.java, Tag.NAME.contains(tagName)), {
                    val tags = it.data.items as ArrayList<Tag>
                    val first = tags.stream().findFirst().get()
                    this._existingTagsList.add(Pair(first.id, first.name))
                }, {
                    Log.e(TAG, "Error finding tag $tagName", it)
                }
                )
            }
        })
    }

    private fun setupViewPager() {
        val steps = arrayListOf(
            InitialTagSelectionFragment(),
            InitialGroupSelectionFragment(),
        )
        vpInitAccount.isUserInputEnabled = false
        vpInitAccount.adapter = InitialAccountViewPagerAdapter(steps, supportFragmentManager, lifecycle)
//        vpInitAccount.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                svRecoverySteps.go(position, true)
//            }
//        })
    }

    override fun onIdListChange(list: List<String>) {
        _tagsList.postValue(list)
    }

    companion object {
        private const val TAG = "InitialAccountSetup"
    }
}