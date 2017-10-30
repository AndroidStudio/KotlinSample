package sample.android.login

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import sample.android.LoadingState
import sample.android.local.models.CustomerModel
import sample.android.remote.models.LoginModel
import sample.android.viewmodel.BaseViewModel
import java.util.concurrent.TimeUnit

class LoginViewModel(application: Application) : BaseViewModel(application) {

    var loadingState: MutableLiveData<LoadingState> = MutableLiveData()

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
            compositeDisposable.add(disposable!!)
        }
    }

    private fun saveCustomer(loginModel: LoginModel): LoginModel {
        val customer = CustomerModel()
        customer.name = loginModel.data?.customer?.name;
        customer.email = loginModel.data?.customer?.email;
        database.query().insertCustomer(customer)
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
}
