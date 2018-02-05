package sample.android.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

class TestFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TestFragment", "onCreate")
    }

}