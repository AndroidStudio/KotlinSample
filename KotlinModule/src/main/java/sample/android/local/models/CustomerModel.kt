package sample.android.local.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import sample.android.Constants.Companion.CUSTOMER_TABLE

@Entity(tableName = CUSTOMER_TABLE)
class CustomerModel {

    @ColumnInfo(name = "id")
    @PrimaryKey()
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "email")
    var email: String? = null
}