package com.employeeinfo.model

data class EmployeeModel(
    val data: List<Data>,
    val status: String // success
) {
    data class Data(
        val employee_age: String, // 61
        val employee_name: String, // Tiger Nixon
        val employee_salary: String, // 320800
        val id: String, // 1
        val profile_image: String
    )
}