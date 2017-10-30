package sample.android.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import sample.android.KotlinApplication
import sample.android.local.Database
import sample.android.local.models.CustomerModel
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var database: Database

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var customerLiveData: MutableLiveData<CustomerModel> = MutableLiveData()

    init {
        KotlinApplication.applicationComponent.inject(this)
    }

    fun getCustomer() {
        compositeDisposable.add(database.customerDao().getCustomer()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { customerModel -> this.customerLiveData.value = customerModel }))
    }
}
