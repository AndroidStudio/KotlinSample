package sample.android.login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import sample.android.KotlinApplication
import sample.android.LoadingState
import sample.android.remote.RemoteRepository
import sample.android.remote.models.LoginResponse
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var remoteRepository: RemoteRepository

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var loadingState: MutableLiveData<LoadingState> = MutableLiveData()

    init {
        KotlinApplication.applicationComponent.inject(this)
    }

    private var disposable: Disposable? = null;

    fun loginUser(email: String, password: String) {
        if (disposable != null && !disposable!!.isDisposed) {
            return
        } else {
            disposable = remoteRepository.loginUser(email, password)
                    .delay(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .map(this::saveUser)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onFailed, this::onCompleted, this::onSubscribe)
            disposable?.let { disposable -> compositeDisposable.add(disposable) }
        }
    }

    private fun saveUser(l: LoginResponse): LoginResponse {
        return l
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

    private fun onSuccess(l: LoginResponse) {
        loadingState.value = LoadingState.SUCCESS(l)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
