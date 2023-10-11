package com.mutsuddi_s.starwars.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.mutsuddi_s.starwars.data.local.AppDatabase
import com.mutsuddi_s.starwars.data.remote.ApiInterface
import com.mutsuddi_s.starwars.data.repository.StarWarsRepository
import com.mutsuddi_s.starwars.utils.Constants.BASE_URL
import com.mutsuddi_s.starwars.utils.Constants.DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    /**
     * Provides the base URL for the Star Wars API.
     *
     * @return The base URL as a String.
     */
    @Singleton
    @Provides
    fun providesBaseUrl(): String {
        return BASE_URL
    }

    /**
     * Provides an HTTP logging interceptor for debugging network requests and responses.
     *
     * @return An instance of HttpLoggingInterceptor.
     */
    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * Provides a Gson converter factory for Retrofit to parse JSON responses.
     *
     * @return A GsonConverterFactory instance.
     */
    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides an OkHttpClient with various settings such as timeouts and logging interceptor.
     *
     * @param httpLoggingInterceptor The logging interceptor.
     * @return An OkHttpClient instance.
     */
    @Singleton
    @Provides
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    /**
     * Provides a Retrofit instance configured with the base URL, converter factory, and OkHttpClient.
     *
     * @param baseUrl The base URL of the API.
     * @param converterFactory The JSON converter factory.
     * @param okHttpClient The OkHttpClient with interceptors.
     * @return A Retrofit instance for making API requests.
     */

    @Singleton
    @Provides
    fun providesRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)

        return retrofit.build()
    }

    /**
     * Provides an instance of the API interface for making network requests.
     *
     * @param retrofit The Retrofit instance.
     * @return An implementation of the API interface.
     */

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }



    /**
     * Provides the local database instance using Room Persistence Library.
     *
     * @param context The application context.
     * @return An instance of the AppDatabase.
     */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE)
            .build()
    }
}