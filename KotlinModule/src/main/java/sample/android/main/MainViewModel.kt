package sample.android.main

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import sample.android.local.models.CustomerModel
import sample.android.viewmodel.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    var customerLiveData: MutableLiveData<CustomerModel> = MutableLiveData()

    fun getCustomer() {
        compositeDisposable.add(database.query().getCustomerCount()
                .flatMap { count ->
                    Log.d("COUNT", "$count")
                    database.query().getCustomer()
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
