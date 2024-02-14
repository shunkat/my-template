package guild.hackathon.ui.List

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import guild.hackathon.di.getScreenModel

class ListScreen: Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ListScreenModel>()
    }
}