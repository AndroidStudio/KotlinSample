package sample.android.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import sample.android.remote.RemoteRepository
import javax.inject.Singleton

@Module
class AndroidModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

}