package guild.hackathon.ui.components

import androidx.compose.runtime.Composable

@Composable
expect fun AlertDialog(text: String, close: () -> Unit)