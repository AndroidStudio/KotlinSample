package sample.android.local.interfaces

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import sample.android.local.models.CustomerModel

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: CustomerModel)

    @Query("SELECT * FROM customer LIMIT 1")
    fun getCustomer(): Flowable<CustomerModel>

    @Query("SELECT COUNT(*) FROM customer")
    fun getCustomerCount(): Flowable<Int>
}