package com.dev.countrypicker.bottomsheet.network

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dev.countrypicker.bottomsheet.network.NetworkURL
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResponseInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            NetworkURL.RES_UNAUTHORISED -> {
            }
        }
        return response
    }
}