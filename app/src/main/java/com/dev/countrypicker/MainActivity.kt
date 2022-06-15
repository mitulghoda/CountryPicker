package com.dev.countrypicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.countrypicker.bottomsheet.ui.BottomSheetCountry
import com.dev.countrypicker.bottomsheet.model.CountryModel
import com.dev.countrypicker.bottomsheet.listner.IObjectCallback
import com.dev.countrypicker.bottomsheet.utils.RegionManager
import com.dev.countrypicker.databinding.ActivityMainBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        RegionManager.init(this)
        mainBinding.tvCountry.setOnClickListener {
            val bottomSheetCountry = BottomSheetCountry(true,true,true)
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