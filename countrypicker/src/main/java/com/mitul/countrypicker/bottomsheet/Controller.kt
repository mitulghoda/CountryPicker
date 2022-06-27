package com.mitul.countrypicker.bottomsheet

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.mitul.countrypicker.bottomsheet.utils.ActivityLifeCycle
import com.mitul.countrypicker.bottomsheet.utils.RegionManager

class Controller : Application(), LifecycleObserver {
    private var activityLifeCycle = ActivityLifeCycle()
    override fun onCreate() {
        super.onCreate()
        @JvmField
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        registerActivityLifecycleCallbacks(activityLifeCycle)
        RegionManager.init(this)
    }

    companion object {
        @JvmField
        var instance: Controller? = null
    }
}
