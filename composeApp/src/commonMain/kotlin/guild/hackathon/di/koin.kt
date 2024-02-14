package guild.hackathon.di

import org.koin.core.KoinApplication
import org.koin.mp.KoinPlatformTools

fun initKoin(additionalConfiguration: KoinApplication.() -> Unit = {}) {
    // koinが初期化されていなかったら初期化する処理
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        KoinPlatformTools.defaultContext().startKoin {
            modules(viewModel, netModule, supabaseModule)
        }
    } else {
        // koinが初期化されていたら何もしない
    }
}