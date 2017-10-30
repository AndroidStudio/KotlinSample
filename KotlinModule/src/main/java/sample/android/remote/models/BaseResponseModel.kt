package sample.android.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponseModel {

    @SerializedName("description")
    @Expose
    var description: Description? = null
    @SerializedName("server")
    @Expose
    var server: Server? = null
    @SerializedName("status")
    @Expose
    var status: Int? = null

    inner class Description {

        @SerializedName("error_code")
        @Expose
        var errorCode: String? = null
        @SerializedName("error_description")
        @Expose
        var errorDescription: String? = null
        @SerializedName("message")
        @Expose
        var message: String? = null
        @SerializedName("validator")
        @Expose
        var validator: Validator? = null

    }

    inner class Server {

        @SerializedName("server_instance")
        @Expose
        var serverInstance: String? = null
        @SerializedName("server_version")
        @Expose
        var serverVersion: String? = null
        @SerializedName("timestamp")
        @Expose
        var timestamp: Int? = null
    }

    inner class Validator {

        @SerializedName("email")
        @Expose
        var email: String? = null

    }
}
