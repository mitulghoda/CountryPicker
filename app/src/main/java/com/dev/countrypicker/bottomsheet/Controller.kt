package com.dev.countrypicker.bottomsheet

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.dev.countrypicker.bottomsheet.utils.ActivityLifeCycle
class Controller : Application(), LifecycleObserver {
    private var activityLifeCycle = ActivityLifeCycle()
    override fun onCreate() {
        super.onCreate()
        @JvmField
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        registerActivityLifecycleCallbacks(activityLifeCycle)
    }
    companion object {
        @JvmField
        var instance: Controller? = null
    }
}
