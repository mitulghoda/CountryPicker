package com.mitul.countrypicker.bottomsheet.utils

import android.content.Context
import com.google.gson.Gson
import com.mitul.countrypicker.bottomsheet.model.CountryData
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class RegionManager private constructor() {
    private fun loadCountries(context: Context) {
        countries = Gson().fromJson<List<CountryData>?>(
            readJsonFromAsset("new_countries.json", context),
            object : TypeToken<List<CountryData?>?>() {}.type
        )
    }

    companion object {
        private var regionManager: RegionManager? = null
        private var countries: List<CountryData>? = null
        val instance: RegionManager?
            get() {
                if (regionManager == null) regionManager = RegionManager()
                return regionManager
            }

        fun init(context: Context) {
            val regionManager = instance
            regionManager!!.loadCountries(context)
        }

        fun getCountries(): ArrayList<CountryData>? {
            try {
                val country = countries?.sortedBy { it.name?.common }?.let { ArrayList(it) }
                return country?.filter {
                    it.getCountryCode().isNotEmpty()
                } as ArrayList<CountryData>?
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        fun readJsonFromAsset(filename: String?, context: Context): String? {
            val manager = context.assets
            var file: InputStream? = null
            try {
                file = manager.open(filename!!)
                val formArray = ByteArray(file.available())
                file.read(formArray)
                file.close()
                return String(formArray)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }
}