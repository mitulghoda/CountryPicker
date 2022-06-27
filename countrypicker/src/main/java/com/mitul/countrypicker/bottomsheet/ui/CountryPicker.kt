package com.mitul.countrypicker.bottomsheet.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitul.countrypicker.R
import com.mitul.countrypicker.bottomsheet.listner.ObjectCallback
import com.mitul.countrypicker.bottomsheet.adapter.CountryPickerAdapter
import com.mitul.countrypicker.bottomsheet.model.CountryData
import com.mitul.countrypicker.bottomsheet.utils.RegionManager
import com.mitul.countrypicker.databinding.BottomSheetCountriesBinding

class CountryPicker(
    var isFlagVisible: Boolean = true,
    var isISOCodeVisible: Boolean = true,
    var isCurrencyVisible: Boolean = true
) : CountryPickerBase(), TextWatcher, OnEditorActionListener,
    ObjectCallback<CountryData?> {
    private var adapter: CountryPickerAdapter? = null
    private var country = ArrayList<CountryData>()
    private var countryObjectCallback: ObjectCallback<CountryData>? = null
    private lateinit var binding: BottomSheetCountriesBinding
    private var viewMap: HashMap<String, Boolean>? = null
    fun setCountryObjectCallback(countryObjectCallback: ObjectCallback<CountryData>?) {
        this.countryObjectCallback = countryObjectCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setExpanded(true)
        setKeyboard(false)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.txtTitle.setText(R.string.select_country)
        viewMap = HashMap()
        viewMap?.put("FLAG", isFlagVisible)
        viewMap?.put("ISO", isISOCodeVisible)
        viewMap?.put("CURRENCY", isCurrencyVisible)
        setRecyclerView()
        getCountryList()
        binding.edtSearch.addTextChangedListener(this)
        binding.edtSearch.setOnEditorActionListener(this)
        binding.edtSearch.requestFocus()
    }


    private fun setRecyclerView() {
        binding.recycleView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                DividerItemDecoration.VERTICAL
            )
        )
        adapter = CountryPickerAdapter(country, viewMap, object : ObjectCallback<CountryData> {
            override fun result(data: CountryData?) {
                if (countryObjectCallback != null) {
                    countryObjectCallback!!.result(data)
                }
                hideBottomSheet()
            }
        })
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(activity)

    }

    private fun getCountryList() {
        adapter?.setData(RegionManager.getCountries()!!)
        binding.progressBar.hide()
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        if (charSequence.toString().isEmpty()) return
        if (adapter == null) return
        adapter!!.filter.filter(charSequence.toString().trim { it <= ' ' })
    }

    override fun afterTextChanged(s: Editable) {}
    override fun onEditorAction(textView: TextView, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            return true
        }
        return false
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        if (countryObjectCallback != null) countryObjectCallback!!.result(null)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (countryObjectCallback != null) countryObjectCallback!!.result(null)
    }

    override fun result(data: CountryData?) {
        if (countryObjectCallback != null) {
            countryObjectCallback!!.result(data)
        }
        hideBottomSheet()
    }

}