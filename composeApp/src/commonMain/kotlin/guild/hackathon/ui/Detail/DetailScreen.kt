package guild.hackathon.ui.Detail

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import guild.hackathon.di.getScreenModel

class DetailScreen: Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<DetailScreenModel>()
    }
}