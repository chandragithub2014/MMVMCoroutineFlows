package com.employeeinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.employeeinfo.adapters.UserListAdapter
import com.employeeinfo.viewmodel.EmployeeViewModel
import com.employeeinfo.viewmodel.EmployeeViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var  employeeViewModel: EmployeeViewModel
    private var userListAdapter : UserListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        initViewModel()
        observeLoading()
        fetchEmployeeList()
        observeEmployeeInfo()
    }
    private fun initAdapter(){
        userListAdapter =  UserListAdapter(arrayListOf(),this)
        userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }
    }
    private fun initViewModel(){
        var employeeViewModelFactory = EmployeeViewModelFactory()
        employeeViewModel = ViewModelProvider(this,employeeViewModelFactory).get(EmployeeViewModel::class.java)
    }

    private fun fetchEmployeeList(){
        employeeViewModel.fetchEmployeeList()
    }

    private  fun observeEmployeeInfo(){
        employeeViewModel.employeeLiveData.observe(this, Observer { response ->
            response?.let { employeeModel ->
               println ("Received Response ${employeeModel.data}")
                employeeModel?.let {
                    userListAdapter?.refreshAdapter(employeeModel.data)
                }
            }
        })
    }

    private fun observeLoading(){
        employeeViewModel.fetchLoadStatus().observe(this, Observer {
            if (!it) {
                println(it)
                loading_progressBar.visibility = View.GONE
            }else{
                loading_progressBar.visibility = View.VISIBLE
            }

        })
    }
}