package concept.omdb.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import concept.omdb.BuildConfig
import concept.omdb.data.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provide API dependencies for [AppComponent] and testing
 */
@Module
class ApiModule {

    private val baseUrl = "https://www.omdbapi.com/"
    private val connectionTimeout = 30 // seconds
    private val readTimeout = 30 // seconds

    private val isDebug = BuildConfig.DEBUG

    private val gson: Gson get() = GsonBuilder().create()

    private val interceptor: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply { level = if (isDebug) BODY else NONE }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val client = buildClient()
        val retrofit = buildRetrofit(client)
        return retrofit.create(ApiService::class.java)
    }

    private fun buildRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private fun buildClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(connectionTimeout.toLong(), TimeUnit.SECONDS)
        .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
        .build()

}