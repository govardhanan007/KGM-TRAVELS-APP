package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.KgmApp
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.KgmViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val viewModel: KgmViewModel = viewModel()
      val settings by viewModel.appSettings.collectAsStateWithLifecycle()
      val isDarkModeSetting = settings["dark_mode"] ?: "false"
      val systemDark = isSystemInDarkTheme()
      val darkTheme = when (isDarkModeSetting) {
          "true" -> true
          "false" -> false
          else -> systemDark
      }
      MyApplicationTheme(darkTheme = darkTheme) {
        KgmApp(viewModel = viewModel)
      }
    }
  }
}
