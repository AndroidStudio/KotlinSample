package sample.android.login

import android.app.Application
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import sample.android.LoadingState
import sample.android.local.models.CustomerModel
import sample.android.remote.models.LoginModel
import sample.android.viewmodel.BaseViewModel
import java.util.concurrent.TimeUnit

class LoginViewModel(application: Application) : BaseViewModel(application) {

    var state: BehaviorSubject<LoadingState> =
            BehaviorSubject.createDefault(LoadingState.FINISHED())

    fun loginUser(email: String, password: String) {
        if (state.value is LoadingState.STARTED) {
            return
        }

        state.onNext(LoadingState.STARTED())
        compositeDisposable.add(remoteRepository.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .delay(10, TimeUnit.SECONDS)
                .map(::saveCustomer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onSuccess, ::onFailed, ::onCompleted))
    }

    private fun saveCustomer(loginModel: LoginModel): LoginModel {
        val customer = CustomerModel()
        customer.name = loginModel.data?.customer?.name;
        customer.email = loginModel.data?.customer?.email;
        database.query().insertCustomer(customer)
        return loginModel
    }

    private fun onCompleted() {
        state.onNext(LoadingState.FINISHED())
    }

    private fun onFailed(throwable: Throwable) {
        state.onNext(LoadingState.ERROR(throwable.message))
    }

    infix fun forceChangeState(state: LoadingState) {
        LoginViewModel@this.state.onNext(state)
    }

    private fun onSuccess(loginModel: LoginModel) {
        state.onNext(LoadingState.SUCCESS(loginModel))
    }
}
