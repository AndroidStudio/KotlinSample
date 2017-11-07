package sample.android.annotation

import android.util.Log

open class AnnotationSample {

    companion object {
        private val TAG = "AnnotationSample"
    }

    @Status(50)
    @Throws(Exception::class)
    open fun onResult() {
        val method =  AnnotationSample::class.java.getMethod("onResult")
        val statusAnnotation = method.getAnnotation(Status::class.java)
        val value = statusAnnotation.value
        Log.e(TAG, "onResult: $value")
    }
}


