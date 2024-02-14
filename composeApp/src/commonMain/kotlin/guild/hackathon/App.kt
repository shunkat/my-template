package guild.hackathon

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import guild.hackathon.di.initKoin
import guild.hackathon.theme.AppTheme
import guild.hackathon.ui.Login.LoginScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() = AppTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        initKoin()
        val screens = listOf(LoginScreen())
        Navigator(screens)
    }
}

internal expect fun openUrl(url: String?)