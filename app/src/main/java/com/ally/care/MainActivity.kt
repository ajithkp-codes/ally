package com.ally.care

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ally.care.ui.AllyApp
import com.ally.care.ui.AllyViewModel
import com.ally.care.ui.theme.AllyTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AllyViewModel by viewModels {
        AllyViewModel.factory((application as AllyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AllyApp(viewModel)
                }
            }
        }
    }
}
