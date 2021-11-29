package com.example.palfinder.views.user.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.example.palfinder.R
import com.example.palfinder.backend.services.UserData
import com.example.palfinder.components.OnIdListChange
import com.example.palfinder.views.HomeActivity
import kotlinx.android.synthetic.main.activity_initial_account_setup.*

class InitialAccountSetup : AppCompatActivity(), OnIdListChange {
    private val _existingTagsList = mutableListOf<Pair<String, String>>()
    private var groupsList = MutableLiveData<List<String>>()
    private val initialSetupModel: InitialSetupViewModel by viewModels()

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
        btnContinue.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
    }

    private fun setupViewPager() {
        val steps = arrayListOf(
            InitialTagSelectionFragment(),
            InitialGroupSelectionFragment(),
            InitialSetupConfirmationFragment(),
        )
//        vpInitAccount.isUserInputEnabled = false
        vpInitAccount.adapter =
            InitialAccountViewPagerAdapter(steps, supportFragmentManager, lifecycle)
        vpInitAccount.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    2 -> {
                        btnContinue.visibility = View.GONE
                        btnSave.visibility = View.VISIBLE
                    }
                    else -> {
                        btnContinue.visibility = View.VISIBLE
                        btnSave.visibility = View.GONE
                    }
                }
            }
        })
    }

    override fun onIdListChange(list: List<String>) {
        initialSetupModel.tagsList.postValue(list)
    }

    companion object {
        private const val TAG = "InitialAccountSetup"
    }
}