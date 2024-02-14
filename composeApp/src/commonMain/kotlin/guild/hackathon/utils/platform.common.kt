package guild.hackathon.utils

import com.russhwolf.settings.Settings

val settings: Settings = Settings()

fun getUserId(): Int? {
    return settings.getIntOrNull("uuid")
}

fun setUserId(uuid: Int?) {
    settings.putInt("user", uuid ?: 0)
}