package com.mitul.countrypicker.bottomsheet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mitul.countrypicker.R
import com.mitul.countrypicker.bottomsheet.listner.IObjectCallback
import com.mitul.countrypicker.bottomsheet.model.CountryModel
import com.mitul.countrypicker.databinding.CustomItemCountryBinding

class NewCountryAdapter(
    countries: List<CountryModel>?,
    countryIObjectCallback: IObjectCallback<CountryModel>?
) :
    RecyclerView.Adapter<NewCountryAdapter.ViewHolder>(), Filterable {
    private var countries: MutableList<CountryModel>
    private var temp: List<CountryModel>
    private val countryIObjectCallback: IObjectCallback<CountryModel>?
    var binding: CustomItemCountryBinding? = null
    fun getItem(position: Int): CountryModel? {
        return countries[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CustomItemCountryBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_item_country, parent, false)
        )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.binding.data = country
        holder.itemView.setOnClickListener { v: View? ->
            countryIObjectCallback?.response(
                countries[holder.adapterPosition]
            )
        }
    }

    override fun getItemId(position: Int): Long {
        return countries[position].name.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class ViewHolder(val binding: CustomItemCountryBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    private inner class Filtration : Filter() {
        var suggestions: MutableList<CountryModel>? = null
        override fun convertResultToString(`object`: Any): CharSequence {
            return (`object` as CountryModel).name.toString()
        }

        override fun performFiltering(charSequence: CharSequence): FilterResults {
            suggestions = ArrayList()
            for (country in temp) {
                if (country.getCountryCode()
                        .contains(charSequence.toString()) || country.name?.official?.lowercase()!!
                        .contains(charSequence.toString().lowercase())
                ) {
                    (suggestions as ArrayList<CountryModel>).add(country)
                }
            }
            val filterResults = FilterResults()
            filterResults.values = suggestions
            filterResults.count = (suggestions as ArrayList<CountryModel>).size
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, results: FilterResults) {
            val result = results.values as List<*>
            if (suggestions != null && result.isNotEmpty()) {
                countries = ArrayList(suggestions)
            } else {
                countries.clear()
            }
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return Filtration()
    }

    fun setData(country: ArrayList<CountryModel>) {
        countries = country
        temp = country
        notifyDataSetChanged()
    }

    init {
        this.countries = countries?.let { ArrayList(it) }!!
        temp = ArrayList(countries)
        this.countryIObjectCallback = countryIObjectCallback
    }
}