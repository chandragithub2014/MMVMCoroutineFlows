package com.employeeinfo.viewmodel

import androidx.lifecycle.*
import com.employeeinfo.model.EmployeeModel
import com.employeeinfo.model.UserModel
import com.employeeinfo.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EmployeeViewModel(private val dispatcher: CoroutineDispatcher, private val employeeRepository: EmployeeRepository) : ViewModel(),LifecycleObserver {
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _employeeMutableLiveDate= MutableLiveData<UserModel>()
    val  employeeLiveData: LiveData<UserModel> = _employeeMutableLiveDate

    init {
        loading.postValue(true)
    }
    /**
     * Fetch employee list
     *
     */
    fun fetchEmployeeList(){

       viewModelScope.launch(dispatcher) {
           loading.postValue(true)
           try{
              employeeRepository.fetchEmployees().collect { response->
                  if(response.isSuccessful){
                      loading.postValue(false)
                      val employeeInfo = response.body()
                      employeeInfo?.let { model ->
                          _employeeMutableLiveDate.value = model
                      }


                  }else{
                      loading.postValue(false)
                  }

              }


           }catch (e:Exception){
               loading.postValue(false)
               e.printStackTrace()
           }
       }

     }

    /**
     * Fetch load status
     *
     * @return
     */
    fun fetchLoadStatus(): LiveData<Boolean> = loading
}