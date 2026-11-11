package com.example.util

import android.content.Context
import android.util.Log

object AppIconManager {

    private const val TAG = "AppIconManager"
    private var currentLogoSelection: String? = null

    /**
     * Safely registers the active branding configuration in application memory.
     * Avoids dynamic PackageManager component toggling which triggers ashmem warnings on Android Q+.
     */
    fun setAppIcon(context: Context, logoSelection: String?) {
        currentLogoSelection = logoSelection
        Log.d(TAG, "Active App Logo preset: $logoSelection")
    }

    fun getActiveAppIcon(): String? = currentLogoSelection
}
