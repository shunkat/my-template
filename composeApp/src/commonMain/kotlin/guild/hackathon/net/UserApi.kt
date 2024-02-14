package guild.hackathon.net

import guild.hackathon.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest

interface UserApi {
    suspend fun getUserId(): String?
    suspend fun register(name: String, email: String, password: String): User?
    suspend fun login(email: String, password: String)
}

internal class UserApiImpl(
    private val client : SupabaseClient,
): UserApi {
    override suspend fun getUserId(): String? {
        // Use Supabase auth to get the user id
        return client.gotrue.currentUserOrNull()?.id
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): User? {
        // Use Supabase auth to register the user
        val result = client.gotrue.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        return User(result?.id, name ,email, null)
    }

    override suspend fun login(email: String, password: String){ // Add this method
        // Use Supabase auth to log in the user
        val result = client.gotrue.loginWith(Email) {
            this.email = email
            this.password = password
        }
    }
}