package com.dev.countrypicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.countrypicker.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.mitul.countrypicker.bottomsheet.listner.IObjectCallback
import com.mitul.countrypicker.bottomsheet.model.CountryModel
import com.mitul.countrypicker.bottomsheet.ui.BottomSheetCountry

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.tvCountry.setOnClickListener {
            val bottomSheetCountry = BottomSheetCountry(true, true, true)
            bottomSheetCountry.setCountryObjectCallback(object : IObjectCallback<CountryModel> {
                override fun response(data: CountryModel?) {
                    if (data == null) return
                    mainBinding.tvCountry.text = Gson().toJson(data)
                }
            })
            bottomSheetCountry.show(this.supportFragmentManager, "select_country")
        }
    }

}