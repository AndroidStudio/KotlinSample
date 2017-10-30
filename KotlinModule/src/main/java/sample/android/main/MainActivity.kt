package sample.android.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import sample.android.RecyclerListAdapter
import sample.android.local.models.CustomerModel
import kotlin.sample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.customerLiveData.observe(this, Observer<CustomerModel>
        { customerModel: CustomerModel? -> customerTextView.text = customerModel?.name })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerListAdapter(this)

        customerButton.setOnClickListener { mainViewModel.getCustomer() }
    }
}
