package sample.android.local.interfaces

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import sample.android.Constants.Companion.CUSTOMER_TABLE
import sample.android.local.models.CustomerModel

@Dao
interface DatabaseQuery {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: CustomerModel)

    @Query("SELECT * FROM $CUSTOMER_TABLE LIMIT 1")
    fun getCustomer(): Flowable<CustomerModel>

    @Query("SELECT COUNT(*) FROM $CUSTOMER_TABLE")
    fun getCustomerCount(): Flowable<Int>

}