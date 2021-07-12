package com.employeeinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.employeeinfo.dependency.DependencyUtils
import com.employeeinfo.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers

class EmployeeViewModelFactory : ViewModelProvider.Factory {
    lateinit var employeeRepository: EmployeeRepository
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        employeeRepository = DependencyUtils.provideEmployeeRepository()
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            return EmployeeViewModel(Dispatchers.Main,employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}