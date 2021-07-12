package com.employeeinfo.dependency

import com.employeeinfo.network.EmployeeService
import com.employeeinfo.network.NetworkUtil
import com.employeeinfo.repository.EmployeeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DependencyUtils {

    private fun provideNetworkURL(): String = NetworkUtil.NETWORK_BASE_URL

    private fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY)

    private fun provideOKHttp(logger: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        with(okHttpClient) {
            addInterceptor(logger)
            callTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(NetworkUtil.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        }
        return okHttpClient.build()
    }


     fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(provideNetworkURL()).client(provideOKHttp(
            provideHttpLogger())).addConverterFactory(GsonConverterFactory.create()).build()

    }

    private fun provideAPIService() :EmployeeService{

       return  provideRetrofitInstance().create(EmployeeService::class.java)
    }

    fun provideEmployeeRepository() = EmployeeRepository(provideAPIService())


}