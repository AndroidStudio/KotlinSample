package sample.android.login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import sample.android.KotlinApplication
import sample.android.LoadingState
import sample.android.local.Database
import sample.android.local.models.CustomerModel
import sample.android.remote.RemoteRepository
import sample.android.remote.models.LoginModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var remoteRepository: RemoteRepository
    @Inject
    lateinit var database: Database

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    init {
        KotlinApplication.applicationComponent.inject(this)
        Log.d("application", application.packageName)
    }

    private var disposable: Disposable? = null;

    fun loginUser(email: String, password: String) {
        if (disposable != null && !disposable!!.isDisposed) {
            return
        } else {
            disposable = remoteRepository.loginUser(email, password)
                    .subscribeOn(Schedulers.io())
                    .delay(1, TimeUnit.SECONDS)
                    .map(this::saveCustomer)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onFailed, this::onCompleted, this::onSubscribe)
            disposable?.let { disposable -> compositeDisposable.add(disposable) }
        }
    }

    private fun saveCustomer(loginModel: LoginModel): LoginModel {
        val customer = CustomerModel()
        customer.name = loginModel.data?.customer?.name;
        customer.email = loginModel.data?.customer?.email;
        database.customerDao().insertCustomer(customer)
        System.out.println("saveCustomer: " + Thread.currentThread().getName());
        return loginModel
    }

    private fun onSubscribe(d: Disposable) {
        loadingState.value = LoadingState.STARTED()
    }

    private fun onCompleted() {
        loadingState.value = LoadingState.FINISHED()
    }

    private fun onFailed(throwable: Throwable) {
        loadingState.value = LoadingState.ERROR(throwable.message)
    }

    private fun onSuccess(loginModel: LoginModel) {
        loadingState.value = LoadingState.SUCCESS(loginModel)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
