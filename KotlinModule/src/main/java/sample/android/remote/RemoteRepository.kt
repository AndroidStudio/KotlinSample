package sample.android.remote

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import sample.android.remote.models.LoginModel

interface RemoteRepository {

    @FormUrlEncoded
    @POST("/api/merchants/auth.json")
    fun loginUser(@Field("email") email: String, @Field("pin") pin: String): Observable<LoginModel>

}