package sample.android

import android.app.Application
import sample.android.component.ApplicationComponent
import sample.android.component.DaggerApplicationComponent
import sample.android.modules.NetworkModule
import kotlin.sample.BuildConfig

class KotlinApplication : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(NetworkModule(BuildConfig.BASE_URL)).build()
    }
}