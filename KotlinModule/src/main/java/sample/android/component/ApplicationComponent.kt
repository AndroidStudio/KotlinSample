package sample.android.component

import dagger.Component
import sample.android.modules.DatabaseModule
import sample.android.modules.NetworkModule
import sample.android.viewmodel.BaseViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(DatabaseModule::class, NetworkModule::class))
interface ApplicationComponent {

    fun inject(baseViewModel: BaseViewModel)

}