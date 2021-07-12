package com.employeeinfo.repository

import com.employeeinfo.model.EmployeeModel
import com.employeeinfo.model.UserModel
import com.employeeinfo.network.EmployeeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class EmployeeRepository(private  val apiServiceAPI: EmployeeService) {

    suspend fun fetchEmployees() : Flow<Response<UserModel>> {
        return  flow {
            val employeeInfo = apiServiceAPI.fetchEmployee()
            emit(employeeInfo)
        }
    }
}