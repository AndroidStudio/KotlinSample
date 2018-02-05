package sample.android.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginModel : BaseModel() {

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Customer {

        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("lat")
        @Expose
        var lat: Float? = null
        @SerializedName("lng")
        @Expose
        var lng: Float? = null
        @SerializedName("address")
        @Expose
        var address: String? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("email")
        @Expose
        var email: String? = null
    }

    inner class Data {

        @SerializedName("customer")
        @Expose
        var customer: Customer? = null
        @SerializedName("token")
        @Expose
        var token: String? = null
    }
}
