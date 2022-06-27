package com.dev.countrypicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.countrypicker.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.mitul.countrypicker.bottomsheet.listner.ObjectCallback
import com.mitul.countrypicker.bottomsheet.model.CountryData
import com.mitul.countrypicker.bottomsheet.ui.CountryPicker
import com.mitul.countrypicker.bottomsheet.utils.RegionManager

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        RegionManager.init(this)
        mainBinding.tvCountry.setOnClickListener {
            val countryPicker = CountryPicker()
            countryPicker.isCurrencyVisible = true
            countryPicker.isISOCodeVisible = true
            countryPicker.setCountryObjectCallback(object : ObjectCallback<CountryData> {
                override fun result(data: CountryData?) {
                    if (data == null) return
                    mainBinding.tvCountry.text = Gson().toJson(data)
                }
            })
            countryPicker.show(this.supportFragmentManager, "select_country")
        }
    }

}