package guild.hackathon.ui.Login

import cafe.adriel.voyager.core.model.ScreenModel
import com.russhwolf.settings.set
import guild.hackathon.net.UserApi
import guild.hackathon.utils.settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenModel(
    private val userApi: UserApi,
): ScreenModel {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val loginStatus = MutableStateFlow<Boolean>(false)
    val loginAlert = MutableStateFlow<String?>(null)

    fun signUp(name: String, email: String, password: String) {
        coroutineScope.launch {
            kotlin.runCatching {
                userApi.register(name, email, password)
            }.onSuccess {
                saveCredentials(name, email, password)
                loginStatus.value = true
            }.onFailure {
                loginAlert.value = "There was an error while registering: ${it.message}"
            }
        }
    }

    private fun saveCredentials(name: String?, email: String, password: String){
        name?.let {
            settings["name"] = it
        }
        // Save the email and password to settings
        settings["email"] = email
        settings["password"] = password
    }

    fun getSavedCredentials(): Pair<String?, String?> {
        val email = settings.getStringOrNull("email")
        val password = settings.getStringOrNull("password")
        return Pair(email, password)
    }

    fun login(email: String, password: String) {
        coroutineScope.launch {
            kotlin.runCatching {
                userApi.login(email, password)
            }.onSuccess {
                saveCredentials(null, email, password)
                loginStatus.value = true
            }.onFailure {
                loginAlert.value = "There was an error while logging in. Check your credentials and try again."
            }
        }
    }
}