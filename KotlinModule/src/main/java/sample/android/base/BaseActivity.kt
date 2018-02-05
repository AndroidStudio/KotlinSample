package sample.android.base

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    operator fun CompositeDisposable.plus(d: Disposable) = add(d)

    fun CompositeDisposable.ile() = this.size()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}