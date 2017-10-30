package sample.android.component

import dagger.Component
import sample.android.modules.AndroidModule
import sample.android.modules.NetworkModule
import sample.android.login.LoginViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, NetworkModule::class))
interface ApplicationComponent {

    fun inject(loginViewModel: LoginViewModel)

}