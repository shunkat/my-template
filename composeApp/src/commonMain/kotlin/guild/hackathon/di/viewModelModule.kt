package guild.hackathon.di

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import guild.hackathon.ui.Detail.DetailScreenModel
import guild.hackathon.ui.List.ListScreenModel
import guild.hackathon.ui.Login.LoginScreenModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

@Composable
inline fun <reified T : ScreenModel> Screen.getScreenModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T {
    // Koin DIフレームワークを使用して依存関係を取得
    val koin = KoinPlatform.getKoin()
    // rememberScreenModelを使用して画面の状態を保持。画面が再構築されても状態が維持される。
    return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
}

// ViewModel層の依存関係を設定するKoinモジュール
val viewModel = module {
    // LoginScreenModelのインスタンスを生成するためのファクトリを定義
    factory {
        LoginScreenModel(get())
    }

    factory {
        ListScreenModel()
    }

    factory {
        DetailScreenModel()
    }
}