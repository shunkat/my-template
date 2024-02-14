package guild.hackathon.ui.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import guild.hackathon.theme.LocalThemeIsDark
import guild.hackathon.di.getScreenModel
import guild.hackathon.ui.List.ListScreen
import guild.hackathon.ui.components.AlertDialog

@ExperimentalMaterial3Api
class LoginScreen(): Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LoginScreenModel>()
        val savedCredentials = screenModel.getSavedCredentials()
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf(savedCredentials.first ?: "") }
        var password by remember { mutableStateOf(savedCredentials.second ?: "") }
        var passwordVisibility by remember { mutableStateOf(false) }
        var signUp by remember { mutableStateOf(false) }
        val loginAlert by screenModel.loginAlert.collectAsState()
        val loginStatus by screenModel.loginStatus.collectAsState()
        val navigator = LocalNavigator.currentOrThrow



        Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.weight(1.0f))

                var isDark by LocalThemeIsDark.current
                IconButton(
                    onClick = { isDark = !isDark }
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp).size(20.dp),
                        imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = null
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = signUp,
                    onCheckedChange = { signUp = it }
                )
                Text("sign up?")
            }

            // チェックボックスがチェックされている場合のみ、nameのためのtextfieldを表示
            if (signUp) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        val imageVector =
                            if (passwordVisibility) Icons.Default.Close else Icons.Default.Edit
                        Icon(
                            imageVector,
                            contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                        )
                    }
                }
            )

            Button(
                onClick = {
                    if (signUp) {
                        screenModel.signUp(name, email, password)
                    } else {
                        screenModel.login(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Login")
            }
        }
        if(loginAlert != null) {
            AlertDialog(loginAlert!!) {
                screenModel.loginAlert.value = null
            }
        }
        if (loginStatus == true) {
            navigator.push(ListScreen())
            screenModel.loginStatus.value = false
        }
    }
}