package sample.android

sealed class LoadingState {

    class STARTED : LoadingState()
    class FINISHED : LoadingState()
    class ERROR(var error: String?) : LoadingState()
    class SUCCESS<T>(var model: T) : LoadingState()

}