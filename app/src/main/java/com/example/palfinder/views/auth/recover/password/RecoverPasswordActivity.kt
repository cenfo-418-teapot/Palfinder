package com.example.palfinder.views.auth.recover.password

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.palfinder.R
import kotlinx.android.synthetic.main.activity_recover_password.*

interface OnStepChange {
    fun onStepChange(step: Int)
}

class RecoverPasswordActivity : AppCompatActivity(R.layout.activity_recover_password), OnStepChange {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val steps = arrayListOf("Get Verification Code", "Change Password")
        svRecoverySteps.state
            .steps(steps)
            .stepsNumber(steps.size)
            .commit()
        setupViewPager()
//        Uncomment to enable manual motion with clicks on stepper
//        svRecoverySteps.setOnStepClickListener { position ->
//            vpRecoverySteps.setCurrentItem(
//                position,
//                false
//            )
//        }
    }

    private fun setupViewPager() {
        vpRecoverySteps.isUserInputEnabled = false
        vpRecoverySteps.adapter = RecoverPasswordViewPagerAdapter(supportFragmentManager, lifecycle)
        vpRecoverySteps.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                svRecoverySteps.go(position, true)
            }
        })
    }

    override fun onStepChange(step: Int) {
       vpRecoverySteps.setCurrentItem(step, false)
    }
}