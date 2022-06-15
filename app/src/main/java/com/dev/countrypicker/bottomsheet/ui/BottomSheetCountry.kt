package com.dev.countrypicker.bottomsheet.ui

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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.countrypicker.R
import com.dev.countrypicker.bottomsheet.listner.IObjectCallback
import com.dev.countrypicker.bottomsheet.adapter.NewCountryAdapter
import com.dev.countrypicker.bottomsheet.model.CountryModel
import com.dev.countrypicker.bottomsheet.viewmodel.CountryViewModel
import com.dev.countrypicker.databinding.BottomSheetCountriesBinding
import com.dev.countrypicker.bottomsheet.network.typeCall
import com.dev.countrypicker.bottomsheet.utils.RegionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetCountry(
    var isFlagVisible: Boolean,
    var isISOCodeVisible: Boolean,
    var isCurrencyVisible: Boolean
) : BaseBottomSheet(), TextWatcher, OnEditorActionListener,
    IObjectCallback<CountryModel?> {

    private val viewModel by viewModels<CountryViewModel>()
    private var adapter: NewCountryAdapter? = null
    private var country = ArrayList<CountryModel>()
    private var countryObjectCallback: IObjectCallback<CountryModel>? = null
    private lateinit var binding: BottomSheetCountriesBinding
    fun setCountryObjectCallback(countryObjectCallback: IObjectCallback<CountryModel>?) {
        this.countryObjectCallback = countryObjectCallback
    }

    fun setCountry(country: List<CountryModel>) {
        this.country = country as ArrayList<CountryModel>
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
        setExpanded(false)
        setKeyboard(false)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.txtTitle.setText(R.string.select_country)
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
        adapter = NewCountryAdapter(country, object : IObjectCallback<CountryModel> {
            override fun response(data: CountryModel?) {
                if (countryObjectCallback != null) {
                    countryObjectCallback!!.response(data)
                }
                hideBottomSheet()
            }
        })
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(activity)

    }

    private fun getCountryList() {
        viewModel.getCountries().observe(this) { resource ->
            resource.status.typeCall(
                success = {
                    binding.progressBar.hide()
                    if (resource.data != null) {
                        country = resource.data.sortedBy { it.name?.common }
                            .let { java.util.ArrayList(it) }
                        adapter?.setData(country)
                    }
                },
                error = {
                    adapter?.setData(RegionManager.getCountries()!!)
                    binding.progressBar.hide()
                },
                loading = { binding.progressBar.show() }
            )
        }
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
        if (countryObjectCallback != null) countryObjectCallback!!.response(null)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (countryObjectCallback != null) countryObjectCallback!!.response(null)
    }

    override fun response(data: CountryModel?) {
        if (countryObjectCallback != null) {
            countryObjectCallback!!.response(data)
        }
        hideBottomSheet()
    }
}