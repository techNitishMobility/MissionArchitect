package com.example.missionarchitect.di

import com.example.missionarchitect.data.remote.ApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            // नियम: प्रोडक्शन में कभी भी 'BODY' लेवल लॉगिंग नहीं होनी चाहिए वरना सेंसिटिव डेटा लीक हो जाएगा
            level = HttpLoggingInterceptor.Level.HEADERS
        }

        return OkHttpClient.Builder()
            // 1. टाइमआउट सिक्योरिटी (DoS अटैक और हैंगिंग कनेक्शन से बचाव)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val securedRequest = originalRequest.newBuilder()
                    // हर रिक्वेस्ट में कस्टम सिक्योर हेडर्स जोड़ना (Anti-Tampering / Client Identification)
                    .header("X-App-Version", "1.0.0")
                    .header("X-Platform", "Android")
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(securedRequest)
            }

            // 4. लॉगिंग इंटरसेप्टर (डिबगिंग के लिए)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}