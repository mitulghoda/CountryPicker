package com.mitul.countrypicker.bottomsheet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mitul.countrypicker.R
import com.mitul.countrypicker.bottomsheet.listner.ObjectCallback
import com.mitul.countrypicker.bottomsheet.model.CountryData
import com.mitul.countrypicker.databinding.CustomItemCountryBinding

class CountryPickerAdapter(
    var countries: ArrayList<CountryData>?,
    val viewMap: HashMap<String, Boolean>?,
    var countryIObjectCallback: ObjectCallback<CountryData>?
) :
    RecyclerView.Adapter<CountryPickerAdapter.ViewHolder>(), Filterable {
    private var temp: List<CountryData>
    var binding: CustomItemCountryBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CustomItemCountryBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_item_country, parent, false)
        )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries?.get(position)
        val flag = viewMap?.get("FLAG")
        val currency = viewMap?.get("CURRENCY")
        val isoCode = viewMap?.get("ISO")
        holder.binding.data = country
        holder.binding.tvISO.visibility = View.VISIBLE.takeIf { isoCode == true } ?: View.GONE
        holder.binding.txtCurrency.visibility = View.VISIBLE.takeIf { currency == true } ?: View.GONE
        holder.binding.imageView.visibility = View.VISIBLE.takeIf { flag == true } ?: View.GONE
        holder.itemView.setOnClickListener { v: View? ->
            countryIObjectCallback?.result(
                countries?.get(holder.adapterPosition)
            )
        }
    }

    override fun getItemId(position: Int): Long {
        return countries?.get(position)?.name.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return countries?.size ?: 0
    }

    class ViewHolder(val binding: CustomItemCountryBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    private inner class Filtration : Filter() {
        var suggestions: MutableList<CountryData>? = null
        override fun convertResultToString(`object`: Any): CharSequence {
            return (`object` as CountryData).name.toString()
        }

        override fun performFiltering(charSequence: CharSequence): FilterResults {
            suggestions = ArrayList()
            for (country in temp) {
                if (country.getCountryCode()
                        .contains(charSequence.toString()) || country.name?.official?.lowercase()!!
                        .contains(charSequence.toString().lowercase())
                ) {
                    (suggestions as ArrayList<CountryData>).add(country)
                }
            }
            val filterResults = FilterResults()
            filterResults.values = suggestions
            filterResults.count = (suggestions as ArrayList<CountryData>).size
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, results: FilterResults) {
            val result = results.values as List<*>
            if (suggestions != null && result.isNotEmpty()) {
                countries = ArrayList(this.suggestions ?: ArrayList())
            } else {
                countries?.clear()
            }
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return Filtration()
    }

    fun setData(country: ArrayList<CountryData>) {
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