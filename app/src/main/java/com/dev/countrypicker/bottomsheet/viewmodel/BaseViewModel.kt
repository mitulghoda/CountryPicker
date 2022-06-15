package com.dev.countrypicker.bottomsheet.viewmodel

import androidx.lifecycle.ViewModel
import com.dev.countrypicker.bottomsheet.network.Resource
import com.dev.countrypicker.bottomsheet.network.ResponseHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {
    var jobs = HashMap<String, Job>()

    val dispatcher by lazy {
        Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Resource.error(data = null, message = ResponseHandler.handleErrorResponse(throwable))
        }
    }

    fun addNewJob(name: String, job: Job) {
        this.jobs[name] = job
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.value.cancel() }
    }
}