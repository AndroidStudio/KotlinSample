package sample.android.model

class Model : BaseModel {

    var name: String? = null

    override fun getViewType(): Int {
        return 1;
    }

}