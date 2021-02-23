package com.example.android.opstringbug

import android.app.AppOpsManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.example.android.opstringbug.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AppOpsManager.OnOpChangedListener {

    lateinit var binding: ActivityMainBinding

    private lateinit var appOpsListenerHelper: AppOpsListenerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appOpsListenerHelper = AppOpsListenerHelper(applicationContext)

        val operations = arrayOf(AppOpsManager.OPSTR_GET_USAGE_STATS, AppOpsManager.OPSTR_WRITE_SETTINGS)
        appOpsListenerHelper.startWatchingAppOps(this, *operations)

        with(binding) {

            changeSystemSettingsButton.setOnClickListener {
                startActivity(Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS))
            }

            usageAccessButton.setOnClickListener {
                startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
            }
        }
    }

    override fun onOpChanged(op: String?, packageName: String?) {
        val operation = op ?: return

        if (packageName == applicationContext.packageName) {
            if (AppOpsManager.OPSTR_GET_USAGE_STATS == operation ||
                    AppOpsManager.OPSTR_WRITE_SETTINGS == operation
            ) {
                Log.d("MainActivity", operation)
                Handler(Looper.getMainLooper()).postDelayed(100) {
                    Toast.makeText(this, "Operation= $operation", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appOpsListenerHelper.stopWatchingAppOps(this)
    }
}