package com.employeeinfo.network

import com.employeeinfo.model.EmployeeModel
import com.employeeinfo.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface EmployeeService {
//https://reqres.in/api/users
    @GET("/api/users")
    suspend fun fetchEmployee(): Response<UserModel>
}