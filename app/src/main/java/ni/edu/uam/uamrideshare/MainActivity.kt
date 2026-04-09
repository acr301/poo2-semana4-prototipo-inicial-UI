package ni.edu.uam.uamrideshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import ni.edu.uam.uamrideshare.ui.theme.UAMRideShareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UAMRideShareTheme {
                UAMRideShareApp()
            }
        }
    }
}

@Composable
fun UAMRideShareApp() {
    // Estado de navegación simple para el prototipo
    var currentScreen by remember { mutableStateOf("register") }

    when (currentScreen) {
        "register" -> {
            RegistroScreen(onNavigateToProfile = { 
                currentScreen = "profile" 
            })
        }
        "profile" -> {
            StudentProfileScreen(onLogout = { 
                currentScreen = "register" 
            })
        }
    }
}
