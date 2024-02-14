package guild.hackathon.di

import guild.hackathon.net.UserApi
import guild.hackathon.net.UserApiImpl
import org.koin.dsl.module

val netModule = module {
    single<UserApi> { UserApiImpl(get()) }
}