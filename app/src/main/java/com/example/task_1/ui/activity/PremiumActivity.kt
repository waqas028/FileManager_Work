package com.example.task_1.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.task_1.R
import com.example.task_1.databinding.ActivityPremiumBinding


class PremiumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPremiumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.monthlyPkgLayout.setOnClickListener {
            monthlySelectPkg()
        }

        binding.threeDaysLayout.setOnClickListener {
            threeDaysTrialSelectPkg()
        }

        binding.oneTimePkgLayout.setOnClickListener {
            oneTimeSelectPkg()
        }

        binding.monthlyRadioButton.setOnClickListener{
            monthlySelectPkg()
        }

        binding.threeDaysTrialRadioButton.setOnClickListener{
            threeDaysTrialSelectPkg()
        }

        binding.oneTimePkgRadioButton.setOnClickListener{
            oneTimeSelectPkg()
        }

        binding.continueButton.setOnClickListener{
            startActivity(Intent(this,PurchasingActivity::class.java))
        }
    }

    private fun monthlySelectPkg(){
        binding.monthlyRadioButton.isChecked = true
        binding.threeDaysTrialRadioButton.isChecked = false
        binding.oneTimePkgRadioButton.isChecked = false
        binding.monthlyPkgLayout.setBackgroundResource(R.drawable.ic_select_pkg_bg)
        binding.threeDaysLayout.background = null
        binding.oneTimePkgLayout.background = null
        //binding.monthlyDiscountTextview.visibility = View.VISIBLE
        binding.threeDaysTrialDiscountTextview.visibility = View.GONE
        binding.oneTimeDiscountTextview.visibility = View.GONE
    }

    private fun threeDaysTrialSelectPkg(){
        binding.monthlyRadioButton.isChecked = false
        binding.threeDaysTrialRadioButton.isChecked = true
        binding.oneTimePkgRadioButton.isChecked = false
        binding.threeDaysLayout.setBackgroundResource(R.drawable.ic_select_pkg_bg)
        binding.monthlyPkgLayout.background = null
        binding.oneTimePkgLayout.background = null
        binding.monthlyDiscountTextview.visibility = View.GONE
        binding.threeDaysTrialDiscountTextview.visibility = View.VISIBLE
        binding.oneTimeDiscountTextview.visibility = View.GONE
    }

    private fun oneTimeSelectPkg(){
        binding.monthlyRadioButton.isChecked = false
        binding.threeDaysTrialRadioButton.isChecked = false
        binding.oneTimePkgRadioButton.isChecked = true
        binding.oneTimePkgLayout.setBackgroundResource(R.drawable.ic_select_pkg_bg)
        binding.threeDaysLayout.background = null
        binding.monthlyPkgLayout.background = null
        binding.monthlyDiscountTextview.visibility = View.GONE
        binding.threeDaysTrialDiscountTextview.visibility = View.GONE
        //binding.oneTimeDiscountTextview.visibility = View.VISIBLE
    }
}