package com.employeeinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.employeeinfo.model.UserModel
import com.employeeinfo.network.EmployeeService
import com.employeeinfo.repository.EmployeeRepository
import com.employeeinfo.viewmodel.EmployeeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class EmployeeViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var retrofit: Retrofit

    @Mock
    private lateinit var apiHelper: EmployeeService

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<LiveData<UserModel>>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

    }
    private val testDispatcher = TestCoroutineDispatcher()
    @Test
    fun test_fetchEmployeeList() = testDispatcher.runBlockingTest {
        var data = UserModel.Data(
            "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg",
            "george.bluth@reqres.in",
            "George",
            1,
            "Bluth"
        )
        var ad = UserModel.Support("StatusCode Weekly","http://statuscode.org/",)
        var list = arrayListOf<UserModel.Data>()
        list.add(data)
        var retroResponse = UserModel(list,2,6,ad,12,12)
        val employeeViewModel = EmployeeViewModel(testDispatcher,employeeRepository)
        val response = Response.success(retroResponse)
        val channel = Channel<Response<UserModel>>()
        val flow = channel.consumeAsFlow()
        Mockito.`when`(employeeRepository.fetchEmployees()).thenReturn(flow )

       launch {
           channel.send(response)
       }
        employeeViewModel.fetchEmployeeList()
        Assert.assertEquals(1,employeeViewModel.employeeLiveData.value?.data?.size)
        Assert.assertEquals(false, employeeViewModel.fetchLoadStatus()?.value)
    }


/*
  val avatar: String, // https://reqres.in/img/faces/1-image.jpg
        val email: String, // george.bluth@reqres.in
        val first_name: String, // George
        val id: Int, // 1
        val last_name: String // Bluth
 */

}