package sample.android.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.login_layout.*
import sample.android.LoadingState
import sample.android.LoadingState.*
import sample.android.base.BaseActivity
import sample.android.main.MainActivity
import sample.android.remote.models.LoginModel
import kotlin.sample.R


class LoginActivity : BaseActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        loginButton.setOnClickListener { onLoginClick() }

        addFragment(TestFragment(), android.R.id.content)

        val mutableList = MutableList(1, { 1 })
        mutableList.add(1)

        val s: String? = null

        val sum = { x: Int, y: Int -> x + y }
        val result = sum(3, 4)

        build(s, height = 400)

        testA("assd", { it.length })
        testA("ass") { it.length }

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        compositeDisposable + viewModel.state.subscribe(this::onNext)
        compositeDisposable.add(viewModel.state.subscribe(this::onNext))

        viewModel forceChangeState LoadingState.STARTED()
        viewModel.forceChangeState(LoadingState.STARTED())
    }

    private fun logD(s: String) {
        Log.d("length1", "" + s.length)
    }

    private inline fun testA(a: String, b: (String) -> Unit) {
        b.invoke(a)
    }

    fun build(title: String?, width: Int = 800, height: Int = 600) {
        Log.d("title", "" + title)
    }

    private fun onLoginClick() {
        launchActivity<MainActivity> {
            action = "d"
            extras.putString("KEY","VALUE")
        }
    }

    private fun onNext(state: LoadingState) {
        when (state) {
            is STARTED -> {
                showProgress(true)
                Log.d("Loading State", "STARTED")
            }
            is FINISHED -> showProgress(false)
            is ERROR -> {
                showProgress(false)
                Log.d("Loading ERROR", "${state.error}")
            }
            is SUCCESS<*> -> {
                showProgress(false)
                val loginResponse = state.model as LoginModel;
                Log.d("Loading SUCCESS", loginResponse.data?.customer?.name)
            }
        }
    }

    private fun showProgress(showProgress: Boolean) {
        progressBar.visibility = if (showProgress) View.VISIBLE else View.GONE
    }
}
