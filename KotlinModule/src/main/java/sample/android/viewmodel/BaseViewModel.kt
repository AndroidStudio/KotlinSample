package sample.android.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleObserver
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import sample.android.KotlinApplication
import sample.android.local.Database
import sample.android.remote.RemoteRepository
import javax.inject.Inject

open class BaseViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    @Inject
    lateinit var remoteRepository: RemoteRepository
    @Inject
    lateinit var database: Database

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        KotlinApplication.applicationComponent.inject(this)
        Log.d("ViewModel", this.javaClass.simpleName)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}