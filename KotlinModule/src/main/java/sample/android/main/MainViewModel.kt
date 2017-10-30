package sample.android.main

import android.app.Application
import android.arch.lifecycle.*
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import sample.android.KotlinApplication
import sample.android.local.Database
import sample.android.local.models.CustomerModel
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    @Inject
    lateinit var database: Database

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var customerLiveData: MutableLiveData<CustomerModel> = MutableLiveData()

    init {
        KotlinApplication.applicationComponent.inject(this)
        Log.d("application", application.packageName)
    }

    fun getCustomer() {
        compositeDisposable.add(database.customerDao().getCustomerCount()
                .flatMap { count ->
                    Log.d("COUNT", "$count")
                    database.customerDao().getCustomer()
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { customerModel -> this.customerLiveData.value = customerModel }))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d("MainViewModel", "ON_RESUME")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
