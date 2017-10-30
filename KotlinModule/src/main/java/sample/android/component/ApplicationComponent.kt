package sample.android.component

import dagger.Component
import dagger.Provides
import sample.android.login.LoginViewModel
import sample.android.main.MainViewModel
import sample.android.modules.DatabaseModule
import sample.android.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(DatabaseModule::class, NetworkModule::class))
interface ApplicationComponent {

    fun inject(loginViewModel: LoginViewModel)

    fun inject(mainViewModel: MainViewModel)

}