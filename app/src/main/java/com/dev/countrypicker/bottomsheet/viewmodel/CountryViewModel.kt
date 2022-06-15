package com.dev.countrypicker.bottomsheet.viewmodel
import androidx.lifecycle.liveData
import com.dev.countrypicker.bottomsheet.network.Resource
import com.dev.countrypicker.bottomsheet.network.ResponseHandler
import com.dev.countrypicker.bottomsheet.network.ResponseHandler.responseParser
import com.dev.countrypicker.bottomsheet.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository,
) : BaseViewModel() {
    fun getCountries() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            responseParser(repository.getCountries(), this)
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = ResponseHandler.handleErrorResponse(e)))
        }
    }
}