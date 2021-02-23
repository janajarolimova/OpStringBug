package com.example.android.opstringbug

import android.app.AppOpsManager
import android.content.Context

class AppOpsListenerHelper @JvmOverloads constructor(
        private val context: Context,
        private val appOpsManager: AppOpsManager =
                context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
) {

    fun startWatchingAppOps(onOpChangedListener: AppOpsManager.OnOpChangedListener, vararg operations: String) {
        for (operation in operations) {
            appOpsManager.startWatchingMode(operation, context.packageName, onOpChangedListener)
        }
    }

    fun stopWatchingAppOps(onOpChangedListener: AppOpsManager.OnOpChangedListener) {
        appOpsManager.stopWatchingMode(onOpChangedListener)
    }
}