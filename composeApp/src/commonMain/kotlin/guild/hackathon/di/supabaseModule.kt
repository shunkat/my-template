package guild.hackathon.di

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import org.koin.dsl.module
import guild.hackathon.BuildKonfig.SUPABASE_KEY
import guild.hackathon.BuildKonfig.SUPABASE_URL
import io.github.jan.supabase.gotrue.GoTrue

val supabaseModule = module {
    single {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            install(Postgrest)
            install(Storage)
            install(GoTrue)
        }
    }
}