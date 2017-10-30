package sample.android.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sample.android.remote.RemoteRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.sample.BuildConfig

@Module
class NetworkModule(private var baseUrl: String) {

    @Provides
    @Singleton
    fun provideRemoteRepository(retrofit: Retrofit): RemoteRepository {
        return retrofit.create(RemoteRepository::class.java)
    }

    @Provides
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    internal fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .build())
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setLenient()
        return gsonBuilder.create()
    }
}
