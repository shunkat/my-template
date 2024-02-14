import androidx.compose.ui.window.ComposeUIViewController
import guild.hackathon.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
